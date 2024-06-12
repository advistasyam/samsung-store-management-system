package com.samsung.feed.repository;

import com.samsung.entity.Feed;
import com.samsung.util.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.inject.Inject;
import java.util.List;

public class FeedRepository {
  @Inject
  private HibernateUtil hibernateUtil;

  public Feed getById(Long id) {
    Session session = null;
    try {
      session = hibernateUtil.getOrOpenSession();
      return session.get(Feed.class, id);
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  public void insert(Feed feed) {
    Session session = null;
    Transaction tx = null;
    try {
      session = hibernateUtil.openSession();
      tx = session.beginTransaction();
      session.save(feed);
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

  public List<Feed> getAll() {
    Session session = null;
    try {
      session = hibernateUtil.getOrOpenSession();
      return session.createQuery("from Feed", Feed.class).list();
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }
}
