package cn.buk.study.dbuit;

import java.sql.*;

public class DbUtil {
  public static Connection getConnection() throws SQLException {
    Connection con = null;
    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbunit?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai",
            "dbunit", "dbunit");
    return con;
  }

  public static void close(Connection con) {
    try {
      if(con!=null) con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void close(PreparedStatement ps) {
    try {
      if(ps!=null) ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void close(ResultSet rs) {
    try {
      if(rs!=null) rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
