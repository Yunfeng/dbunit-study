package cn.buk.study.dbuit;

public class UserServiceImpl implements UserService {
  private UserDao userDao;

  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  public UserServiceImpl() {
    userDao = new UserDaoImpl();
  }

  public void add(User user) {
    if(load(user.getUsername())!=null)
      throw new UserException("用户名已经存在");
    userDao.add(user);
  }

  public void delete(String username) {
    userDao.delete(username);
  }

  public User load(String username) {
    return userDao.load(username);
  }

  public User login(String username, String password) {
    User u = load(username);
    if(u==null) throw new UserException("用户名不存在");
    if(!u.getPassword().equals(password)) throw new UserException("用户密码不存在");
    return u;
  }
}
