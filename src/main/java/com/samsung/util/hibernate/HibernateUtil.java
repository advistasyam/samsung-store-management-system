package com.samsung.util.hibernate;

import lombok.Getter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
  @Getter
  private final SessionFactory sessionFactory = buildSessionFactory();

  public Session getOrOpenSession() {
    try {
      return sessionFactory.getCurrentSession();
    } catch (HibernateException e) {
      return sessionFactory.openSession();
    }
  }

  public Session openSession() {
    return sessionFactory.openSession();
  }

  private static SessionFactory buildSessionFactory() {
    try {
      StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
          .configure("hibernate.cfg.xml")
          .build();

      Metadata metadata = new MetadataSources(standardRegistry)
          .getMetadataBuilder()
          .build();

      return metadata.getSessionFactoryBuilder().build();
    } catch (Throwable ex) {
      System.err.println("SessionFactory creation failed: " + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }
}