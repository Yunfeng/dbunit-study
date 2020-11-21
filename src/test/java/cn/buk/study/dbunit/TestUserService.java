package cn.buk.study.dbunit;

import cn.buk.study.dbuit.*;
import junit.framework.Assert;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class TestUserService extends AbstractDbUnitTestCase  {
  private UserService us;
  private IDataSet ds;

  @Before
  public void setUp() throws DataSetException, IOException {
    us = new UserServiceImpl(new UserDaoImpl());
    backupOneTable("t_user");
    ds = createDateSet("t_user");
  }

  @Test
  public void testLoad() throws DatabaseUnitException, SQLException {
    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
    User u = us.load("admin");
    EntitiesHelper.assertUser(u);
  }

  @Test
  public void testAddNotExist() throws DatabaseUnitException, SQLException{
    DatabaseOperation.TRUNCATE_TABLE.execute(dbunitCon, ds);
    User u = new User("admin", "123", "管理员");
    us.add(u);
    User tu = us.load("admin");
    u.setId(1); //数据中的自增长数值
    EntitiesHelper.assertUser(tu, u);
  }

  @Test(expected=UserException.class)
  public void testAddExists() throws DatabaseUnitException, SQLException{
    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
    User u = new User(1,"admin", "123", "管理员");
    us.add(u);
  }

  @Test
  public void testDelete() throws DatabaseUnitException, SQLException{
    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
    us.delete("admin");
    User tu = us.load("admin");
    Assert.assertNull(tu);
  }

  @Test
  public void testLogin() throws DatabaseUnitException, SQLException{
    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
    String username="admin";
    String password ="123";
    User tu  = us.login(username, password);
    EntitiesHelper.assertUser(tu);
  }

  @Test(expected=UserException.class)
  public void testNotExistsUserLogin() throws DatabaseUnitException, SQLException{
    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
    String username = "admin1";
    String password = "123";
    us.login(username, password);

  }

  @Test(expected=UserException.class)
  public void testPasswordErrorUserLogin() throws DatabaseUnitException, SQLException{
    DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
    String username = "admin";
    String password = "1234";
    us.login(username, password);
  }

  @After
  public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
    resumeTable();
  }
}
