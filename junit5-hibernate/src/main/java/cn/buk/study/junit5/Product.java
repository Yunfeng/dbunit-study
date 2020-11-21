package cn.buk.study.junit5;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 64, nullable = false, unique = true)
  private String name;

  private float price;

  public Product() {
  }

  public Product(String name, float price) {
    this(null, name, price);
  }

  public Product(Integer id) {
    this.id = id;
  }

  public Product(Integer id, String name, float price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  // standard getters and setters
}
