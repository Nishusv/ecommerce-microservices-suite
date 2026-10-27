package com.training.ecommerce.product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController
@RequestMapping("/api/products")
public class ProductController {
 private final ProductRepository repository;
 public ProductController(ProductRepository repository){this.repository=repository;}
 @GetMapping public List<Product> list(){return repository.findAll();}
 @GetMapping("/{id}") public Product get(@PathVariable Long id){
  return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
 }
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Product create(@Valid @RequestBody Product product){
  product.setName(product.getName().trim()); return repository.save(product);
 }
}
