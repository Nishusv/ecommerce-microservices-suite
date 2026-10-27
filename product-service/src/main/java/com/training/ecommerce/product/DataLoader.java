package com.training.ecommerce.product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
@Component
public class DataLoader implements CommandLineRunner {
 private final ProductRepository repository;
 public DataLoader(ProductRepository repository){this.repository=repository;}
 @Override public void run(String... args){
  if(repository.count()==0){
   repository.save(new Product("Wireless Mouse","Bluetooth wireless mouse",new BigDecimal("799.00")));
   repository.save(new Product("Mechanical Keyboard","Compact mechanical keyboard",new BigDecimal("2499.00")));
   repository.save(new Product("USB-C Hub","Seven-port USB-C hub",new BigDecimal("1899.00")));
  }
 }
}
