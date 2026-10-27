package com.training.ecommerce.order;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
@Entity @Table(name="customer_orders")
public class CustomerOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long productId;
 @Column(nullable=false) private String productName;
 @Column(nullable=false) private Integer quantity;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal unitPrice;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal totalPrice;
 @Column(nullable=false) private Instant createdAt;
 protected CustomerOrder(){}
 public CustomerOrder(Long productId,String productName,Integer quantity,BigDecimal unitPrice){
  this.productId=productId; this.productName=productName; this.quantity=quantity; this.unitPrice=unitPrice;
  this.totalPrice=unitPrice.multiply(BigDecimal.valueOf(quantity)); this.createdAt=Instant.now();
 }
 public Long getId(){return id;} public Long getProductId(){return productId;} public String getProductName(){return productName;}
 public Integer getQuantity(){return quantity;} public BigDecimal getUnitPrice(){return unitPrice;} public BigDecimal getTotalPrice(){return totalPrice;} public Instant getCreatedAt(){return createdAt;}
}
