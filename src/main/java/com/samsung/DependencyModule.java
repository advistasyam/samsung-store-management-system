package com.samsung;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.samsung.admin.handler.AdminHandler;
import com.samsung.admin.repository.AdminRepository;
import com.samsung.admin.route.AdminRoutes;
import com.samsung.admin.service.AdminService;
import com.samsung.product.handler.ProductHandler;
import com.samsung.product.repository.ProductRepository;
import com.samsung.product.route.ProductRoutes;
import com.samsung.product.service.ProductService;
import com.samsung.util.auth.AuthUtils;
import com.samsung.util.hibernate.HibernateUtil;

public class DependencyModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(HibernateUtil.class).in(Singleton.class);
    bind(AuthUtils.class).in(Singleton.class);
    // product module
    bind(ProductRepository.class).in(Singleton.class);
    bind(ProductService.class).in(Singleton.class);
    bind(ProductHandler.class).in(Singleton.class);
    bind(ProductRoutes.class).in(Singleton.class);

    // admin
    bind(AdminRepository.class).in(Singleton.class);
    bind(AdminService.class).in(Singleton.class);
    bind(AdminHandler.class).in(Singleton.class);
    bind(AdminRoutes.class).in(Singleton.class);
  }
}
