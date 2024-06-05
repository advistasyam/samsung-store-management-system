package com.samsung.admin.route;

import com.samsung.admin.handler.AdminHandler;
import ratpack.func.Action;
import ratpack.handling.Chain;

import javax.inject.Inject;

public class AdminRoutes implements Action<Chain> {
  @Inject
  private AdminHandler adminHandler;

  @Override
  public void execute(Chain chain) {
    chain
        .path("", ctx -> ctx.byMethod(method -> {
          method
              .post(adminHandler::postAdmin);
        }))
        .path("login", ctx -> ctx.byMethod(method -> {
          method
              .post(adminHandler::handleLogin);
        }));
  }
}
