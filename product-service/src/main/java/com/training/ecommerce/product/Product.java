package com.training.ecommerce.product;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
@Entity @Table(name="products")
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Column(nullable=false) private String name;
 private String description;
 @NotNull @DecimalMin("0.01") @Column(nullable=false, precision=12, scale=2) private BigDecimal price;
 protected Product() {}
 public Product(String name, String description, BigDecimal price) { this.name=name; this.description=description; this.price=price; }
 public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;} public BigDecimal getPrice(){return price;}
 public void setName(String name){this.name=name;} public void setDescription(String description){this.description=description;} public void setPrice(BigDecimal price){this.price=price;}
}
