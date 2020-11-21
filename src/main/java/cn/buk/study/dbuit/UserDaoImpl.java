package cn.buk.study.dbuit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

  public void add(User user) {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      con = DbUtil.getConnection();
      String sql = "insert into t_user(username,nickname,password) value (?,?,?)";
      ps = con.prepareStatement(sql);
      ps.setString(1, user.getUsername());
      ps.setString(2, user.getNickname());
      ps.setString(3, user.getPassword());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }finally{
      DbUtil.close(ps);
      DbUtil.close(con);
    }

  }

  public void delete(String username) {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      con = DbUtil.getConnection();
      String sql = "delete from t_user where username=?";
      ps = con.prepareStatement(sql);
      ps.setString(1, username);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }finally{
      DbUtil.close(ps);
      DbUtil.close(con);
    }
  }

  public User load(String username) {
    User u = null;
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      con = DbUtil.getConnection();
      String sql = "select * from t_user where username=?";
      ps = con.prepareStatement(sql);
      ps.setString(1, username);
      rs = ps.executeQuery();
      while (rs.next()) {
        if(u==null) u =new User();
        u.setId(rs.getInt("id"));
        u.setNickname(rs.getString("nickname"));
        u.setPassword(rs.getString("password"));
        u.setUsername(rs.getString("username"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }finally{
      DbUtil.close(rs);
      DbUtil.close(ps);
      DbUtil.close(con);
    }

    return u;
  }
}
