package cn.buk.study.dbunit;

import cn.buk.study.dbuit.DbUtil;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

import java.io.*;
import java.sql.SQLException;

import static junit.framework.Assert.assertNotNull;

public class AbstractDbUnitTestCase {
  public static IDatabaseConnection dbunitCon;
  private File tempFile ;

  @BeforeClass  //只执行一次（@before是每一个@test前都要执行）同理@afterClass
  public static void init() throws DatabaseUnitException, SQLException {
    dbunitCon = new DatabaseConnection(DbUtil.getConnection());
  }


  protected IDataSet createDateSet(String tname) throws DataSetException {
    InputStream is = AbstractDbUnitTestCase.class.getClassLoader()
            .getResourceAsStream(tname+".xml");
    assertNotNull("dbunit的基础数据不存在",is);
    return new FlatXmlDataSet(new FlatXmlProducer(
            new InputSource(is)));
  }

  protected void backupAllTable() throws IOException, SQLException, DataSetException{
    IDataSet ds = dbunitCon.createDataSet();
    writeBackupFile(ds);
  }

  private void writeBackupFile(IDataSet ds) throws IOException, DataSetException{
    tempFile = File.createTempFile("back", "xml");
    FlatXmlDataSet.write(ds, new FileWriter(tempFile));
  }

  protected void backupCustomTable(String[] tname) throws DataSetException, IOException{
    QueryDataSet ds = new QueryDataSet(dbunitCon);
    for(String str:tname){
      ds.addTable(str);
    }
    writeBackupFile(ds);
  }

  protected void backupOneTable(String tname) throws DataSetException, IOException{
    backupCustomTable(new String[]{tname});
  }

  protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException{
    IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(
            new InputSource(new FileInputStream(tempFile))));
    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
  }

  @AfterClass
  public static void destory(){
    if(dbunitCon!=null){
      try {
        dbunitCon.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
