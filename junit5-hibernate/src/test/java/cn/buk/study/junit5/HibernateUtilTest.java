package cn.buk.study.junit5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

public class HibernateUtilTest {
  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeAll
  public static void setup() {
    sessionFactory = HibernateUtil.getSessionFactory();
    System.out.println("SessionFactory created");
  }

  @AfterAll
  public static void tearDown() {
    if (sessionFactory != null) sessionFactory.close();
    System.out.println("SessionFactory destroyed");
  }

  @Test
  public void testCreate() {
    System.out.println("Running testCreate...");

    session.beginTransaction();

    Product product = new Product("iPhone 10", 699);
    Integer id = (Integer) session.save(product);

    session.getTransaction().commit();

    Assertions.assertTrue(id > 0);
  }

  @Test
  public void testUpdate() {
  }

  @Test
  public void testGet() {
  }

  @Test
  public void testList() {
  }

  @Test
  public void testDelete() {
  }

  @BeforeEach
  public void openSession() {
    session = sessionFactory.openSession();
    System.out.println("Session created");
  }

  @AfterEach
  public void closeSession() {
    if (session != null) session.close();
    System.out.println("Session closed\n");
  }
}
