package cn.buk.study.dbuit;

public interface UserService {
  public void add(User user);

  public void delete(String username);

  public User load(String username);

  public User login(String username,String password);
}
