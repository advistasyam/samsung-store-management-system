package com.samsung.product.core.response;

import com.samsung.entity.Product;

import java.util.List;

public class ProductListResponse {
  public List<Product> products;
  public Long count;

  public static ProductListResponse from(List<Product> products, Long count) {
    ProductListResponse response = new ProductListResponse();
    response.products = products;
    response.count = count;
    return response;
  }
}
