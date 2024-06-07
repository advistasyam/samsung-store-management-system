package com.samsung.product.repository;

import com.samsung.entity.Product;
import com.samsung.product.core.enums.ProductType;
import com.samsung.util.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ratpack.util.MultiValueMap;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
  @Inject
  private HibernateUtil hibernateUtil;

  public Product getById(Long id) {
    Session session = null;
    try {
      session = hibernateUtil.getOrOpenSession();
      return session.get(Product.class, id);
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  public void insert(Product product) {
    Session session = null;
    Transaction tx = null;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      session.save(product);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  public void update(Product product) {
    Session session = null;
    Transaction tx = null;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      session.update(product);
      tx.commit();
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  public void delete(Long id) {
    Session session = null;
    Transaction tx = null;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      Product product = session.get(Product.class, id);
      if (product != null) {
        session.delete(product);
        tx.commit();
      }
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  public List<Product> getByCondition(String condition, MultiValueMap<String, String> param) {
    Session session = null;
    try {
      session = hibernateUtil.openSession();
      Query<Product> query = session.createQuery(condition, Product.class);
      setListProductCondition(param, query);

      int limit = Integer.parseInt(param.get("pageSize"));
      int offset = (Integer.parseInt(param.get("pageNo")) - 1) * limit;
      query.setFirstResult(offset);
      query.setMaxResults(limit);

      return query.list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
    return new ArrayList<>();
  }

  public Long countByCondition(String condition, MultiValueMap<String, String> param) {
    Session session = null;
    try {
      session = hibernateUtil.openSession();
      Query<Long> query = session.createQuery(condition, Long.class);
      setListProductCondition(param, query);

      return query.getSingleResult();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
    return 0L;
  }

  private void setListProductCondition(MultiValueMap<String, String> param, Query<?> query) {
    if (param.get("type") != null) {
      query.setParameter("type", ProductType.valueOf(param.get("type")));
    }
    if (param.get("name") != null) {
      query.setParameter("name", "%" + param.get("name") + "%");
    }
    if (param.get("maxPrice") != null) {
      query.setParameter("maxPrice", BigDecimal.valueOf(Float.parseFloat(param.get("maxPrice"))));
    }
    if (param.get("minPrice") != null) {
      query.setParameter("minPrice", BigDecimal.valueOf(Float.parseFloat(param.get("minPrice"))));
    }
    if (param.get("startTime") != null) {
      query.setParameter("startTime", Timestamp.valueOf(param.get("startTime")));
    }
    if (param.get("endTime") != null) {
      query.setParameter("endTime", Timestamp.valueOf(param.get("endTime")));
    }
  }
}
