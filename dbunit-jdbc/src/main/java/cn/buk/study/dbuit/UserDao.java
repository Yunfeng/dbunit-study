package cn.buk.study.dbuit;

public interface UserDao {
  public void add(User user);
  public void delete(String username);
  public User load(String username);
}
