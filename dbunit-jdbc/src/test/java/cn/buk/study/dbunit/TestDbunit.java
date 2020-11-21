package cn.buk.study.dbunit;

import cn.buk.study.dbuit.DbUtil;
import cn.buk.study.dbuit.User;
import cn.buk.study.dbuit.UserDao;
import cn.buk.study.dbuit.UserDaoImpl;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;

public class TestDbunit {
  @Test
  public void testLoad(){
    try {
      testBackuTable();
      IDatabaseConnection con = new DatabaseConnection(DbUtil.getConnection());
      /**
       * 1、FlatXmlDataSet用来获取基于属性存储的属性值
       * 2、XMLDataSet用来获取基于节点类型存储的属性值
       */
      IDataSet ds = new FlatXmlDataSet(
              new FlatXmlProducer(
                      new InputSource(TestDbunit.class.getClassLoader()
                              .getResourceAsStream("t_user.xml"))));
      /**
       * 将数据库中的数据清空，并且把测试数据插入
       */
      DatabaseOperation.CLEAN_INSERT.execute(con, ds);

      UserDao ud = new UserDaoImpl();
      User tu = ud.load("admin");
      assertEquals(tu.getId(), 1);
      assertEquals(tu.getUsername(), "admin");
      assertEquals(tu.getPassword(), "123");
      assertEquals(tu.getNickname(), "管理员");
    } catch (DatabaseUnitException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    testResume();
  }

  /**
   * 备份数据库所有的表
   */
  @Test
  public void testBackupAllTable(){
    try {
      IDatabaseConnection con = new DatabaseConnection(DbUtil.getConnection());
      IDataSet ds = con.createDataSet();

      FlatXmlDataSet.write(ds, new FileWriter("c:/test/test.xml"));


    } catch (DatabaseUnitException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  /**
   * 备份某张表
   * @return
   */
  @Test
  public void testBackuTable(){
    try {
      IDatabaseConnection con = new DatabaseConnection(DbUtil.getConnection());
      /*
       * 通过QueryDataSet可以有效的选择要处理的表来作为数据集
       */
      QueryDataSet backup = new QueryDataSet(con);
      /*
       * 添加t_user这张表作为备份表
       */
      backup.addTable("t_user");
      FlatXmlDataSet.write(backup, new FileWriter("c:/test/test1.xml"));

    } catch (DatabaseUnitException | SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 还原数据库表
   */
  @Test
  public void testResume(){
    try {
      IDatabaseConnection con = new DatabaseConnection(DbUtil.getConnection());
      IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(
              new InputSource(new FileInputStream("c:/test/test1.xml"))));
      DatabaseOperation.CLEAN_INSERT.execute(con, ds);
    } catch (DataSetException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (DatabaseUnitException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
