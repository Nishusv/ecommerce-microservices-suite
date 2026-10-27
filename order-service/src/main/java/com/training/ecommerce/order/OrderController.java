package com.training.ecommerce.order;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController @RequestMapping("/api/orders")
public class OrderController {
 private final OrderRepository repository; private final WebClient productClient;
 public OrderController(OrderRepository repository,@Value("${services.product-url:http://localhost:8081}") String productUrl){
  this.repository=repository; this.productClient=WebClient.builder().baseUrl(productUrl).build();
 }
 @GetMapping public List<CustomerOrder> list(){return repository.findAll();}
 @GetMapping("/{id}") public CustomerOrder get(@PathVariable Long id){
  return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found"));
 }
 @PostMapping @ResponseStatus(HttpStatus.CREATED)
 public CustomerOrder create(@Valid @RequestBody CreateOrderRequest request){
  ProductResponse product;
  try {
   product=productClient.get().uri("/api/products/{id}",request.productId()).retrieve()
    .bodyToMono(ProductResponse.class).block();
  } catch(Exception ex) {
   throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not validate product. Check productId and Product Catalog status.");
  }
  if(product==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Product not found");
  return repository.save(new CustomerOrder(product.id(),product.name(),request.quantity(),product.price()));
 }
}
