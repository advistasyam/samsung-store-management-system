package com.samsung.entity;

import com.samsung.product.core.enums.ProductType;
import com.samsung.util.entityconverter.product.ProductTypeAttributeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "text")
  private String description;

  @Column(nullable = false)
  @Convert(converter = ProductTypeAttributeConverter.class)
  private ProductType type;

  @Column(nullable = false, name = "stock_quantity")
  private int stockQuantity;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "created_at")
  private Timestamp createdAt;

  @Column(name = "updated_at")
  private Timestamp updatedAt;

}
