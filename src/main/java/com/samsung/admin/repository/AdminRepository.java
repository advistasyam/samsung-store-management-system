package com.samsung.admin.repository;

import com.samsung.entity.Admin;
import com.samsung.util.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.inject.Inject;

public class AdminRepository {
  @Inject
  private HibernateUtil hibernateUtil;

  public void insert(Admin admin) {
    Session session = null;
    Transaction tx = null;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      session.save(admin);
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

  public Admin findByUsername(String username) {
    Session session = null;
    try {
      session = hibernateUtil.getOrOpenSession();
      String hql = "FROM Admin WHERE username = :username";
      Query<Admin> query = session.createQuery(hql, Admin.class);
      query.setParameter("username", username);
      return query.uniqueResult();
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }
}
