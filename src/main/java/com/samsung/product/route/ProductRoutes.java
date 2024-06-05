package com.samsung.product.route;

import com.samsung.product.handler.ProductHandler;
import ratpack.func.Action;
import ratpack.handling.Chain;

import javax.inject.Inject;

public class ProductRoutes implements Action<Chain> {
  @Inject
  private ProductHandler productHandler;

  @Override
  public void execute(Chain chain) {
    chain
        .path("", ctx -> ctx.byMethod(method -> {
          method
              .get(productHandler::getProducts)
              .post(productHandler::postProduct);
        }))
        .path(":id", ctx -> ctx.byMethod(method -> {
          method
              .get(productHandler::getProductById)
              .put(productHandler::putProduct)
              .delete(productHandler::deleteProduct);
        }));
  }
}
