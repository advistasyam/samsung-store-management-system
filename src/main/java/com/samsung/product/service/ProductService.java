package com.samsung.product.service;

import com.samsung.entity.Product;
import com.samsung.product.repository.ProductRepository;
import ratpack.service.Service;
import ratpack.util.MultiValueMap;

import javax.inject.Inject;
import java.util.List;

public class ProductService implements Service {
  @Inject
  private ProductRepository productRepository;

  public Product findById(Long id) {
    return productRepository.getById(id);
  }

  public List<Product> findByCondition(MultiValueMap<String, String> param) {
    String condition = "from Product where 1=1" + generateProductCondition(param);
    return productRepository.getByCondition(condition, param);
  }

  public Long countByCondition(MultiValueMap<String, String> param) {
    String condition = "select count(*) from Product where 1=1" + generateProductCondition(param);
    return productRepository.countByCondition(condition, param);
  }

  private String generateProductCondition(MultiValueMap<String, String> param) {
    String condition = "";
    if (param.get("type") != null) {
      condition += " and type = :type";
    }
    if (param.get("name") != null) {
      condition += " and name like :name";
    }
    if (param.get("maxPrice") != null) {
      condition += " and price <= :maxPrice";
    }
    if (param.get("minPrice") != null) {
      condition += " and price >= :minPrice";
    }
    if (param.get("startTime") != null) {
      condition += " and createdAt >= :startTime";
    }
    if (param.get("endTime") != null) {
      condition += " and createdAt <= :endTime";
    }
    return condition;
  }

  public void insert(Product product) {
    productRepository.insert(product);
  }

  public void update(Product product) {
    productRepository.update(product);
  }

  public void delete(Long id) {
    productRepository.delete(id);
  }
}
