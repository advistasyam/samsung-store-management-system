package com.samsung.product.handler;

import com.samsung.entity.Product;
import com.samsung.product.core.response.ProductListResponse;
import com.samsung.product.service.ProductService;
import com.samsung.util.auth.AuthUtils;
import com.samsung.util.response.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import ratpack.handling.Context;
import ratpack.jackson.Jackson;
import ratpack.util.MultiValueMap;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ProductHandler {
  @Inject
  private ProductService productService;
  @Inject
  private AuthUtils authUtils;

  /*
      @RequestParam(name = "pageNo") Integer pageNo,
      @RequestParam(name = "pageSize") Integer pageSize,
      @RequestParam(name = "type", required = false) ProductType productType,
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
      @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
      @RequestParam(value = "startTime", required = false) Timestamp startTime,
      @RequestParam(value = "startTime", required = false) Timestamp endTime,
   */
  public void getProducts(Context ctx) {
    MultiValueMap<String, String> params = ctx.getRequest().getQueryParams();
    if (StringUtils.isBlank(params.get("pageNo")) || StringUtils.isBlank(params.get("pageSize"))) {
      ResponseUtil.badRequestResponse(ctx, "pageNo and pageSize cannot be blank");
      return;
    }
    List<Product> products = productService.findByCondition(ctx);
    Long count = productService.countByCondition(ctx);
    ResponseUtil.okResponse(ctx, ProductListResponse.from(products, count));
  }

  public void getProductById(Context ctx) {
    Long id = Long.valueOf(ctx.getPathTokens().get("id"));
    Product product = productService.findById(id);
    ResponseUtil.okResponse(ctx, product);
  }

  public void postProduct(Context ctx) {
    if (authUtils.isNotLogin(ctx)) {
      return;
    }
    ctx.parse(Jackson.fromJson(Product.class)).then(product -> {
      product.setCreatedAt(new Timestamp(new Date().getTime()));
      productService.insert(product);
      ResponseUtil.createdResponse(ctx, product);
    });
  }

  public void putProduct(Context ctx) {
    if (authUtils.isNotLogin(ctx)) {
      return;
    }
    Long id = Long.valueOf(ctx.getPathTokens().get("id"));
    ctx.parse(Jackson.fromJson(Product.class)).then(product -> {
      Product existingProduct = productService.findById(id);
      if (existingProduct == null) {
        ResponseUtil.badRequestResponse(ctx, String.format("product with id: %d is not exist", id));
        return;
      }
      product.setId(id);
      product.setCreatedAt(existingProduct.getCreatedAt());
      product.setUpdatedAt(new Timestamp(new Date().getTime()));
      productService.update(product);
      ResponseUtil.okResponse(ctx, product);
    });
  }

  public void deleteProduct(Context ctx) {
    if (authUtils.isNotLogin(ctx)) {
      return;
    }
    Long id = Long.valueOf(ctx.getPathTokens().get("id"));
    Product existingProduct = productService.findById(id);
    if (existingProduct == null) {
      ResponseUtil.badRequestResponse(ctx, String.format("product with id: %d is not exist", id));
      return;
    }
    productService.delete(id);
    ResponseUtil.okResponse(ctx, true);
  }
}