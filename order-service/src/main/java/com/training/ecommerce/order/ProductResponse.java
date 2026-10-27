package com.training.ecommerce.order;
import java.math.BigDecimal;
public record ProductResponse(Long id,String name,String description,BigDecimal price){}
