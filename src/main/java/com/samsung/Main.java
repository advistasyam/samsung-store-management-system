package com.samsung;

import com.samsung.admin.route.AdminRoutes;
import com.samsung.feed.route.FeedRoutes;
import com.samsung.product.route.ProductRoutes;
import ratpack.guice.Guice;
import ratpack.server.RatpackServer;

public class Main {
  public static void main(String[] args) throws Exception {

    RatpackServer.start(server -> server
        .registry(Guice.registry(registry -> registry.module(DependencyModule.class)))
        .handlers(chain -> chain
            .prefix("products", ProductRoutes.class)
            .prefix("admin", AdminRoutes.class)
            .prefix("feeds", FeedRoutes.class)
            .get("", ctx -> ctx.render("Hello World! Welcome to the Samsung Store Management System"))
        )
    );

  }
}
