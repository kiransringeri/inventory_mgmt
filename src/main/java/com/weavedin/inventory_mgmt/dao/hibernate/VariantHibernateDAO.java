package com.weavedin.inventory_mgmt.dao.hibernate;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.weavedin.inventory_mgmt.Variant;
import com.weavedin.inventory_mgmt.dao.VariantDAO;

/**
 * Hibernate DAO class for managing the storage of {@link Variant}.
 * @author kiransringeri
 *
 */
public class VariantHibernateDAO implements VariantDAO {

  private SessionFactory sessionFactory = null;

  public VariantHibernateDAO(SessionFactory sessionFactory) {
    setSessionFactory(sessionFactory);
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Saves the new item to the database.
   */
  public Variant save(Variant entity) {
    Session session = sessionFactory.getCurrentSession();
    session.save(entity);
    return entity;
  }

  /**
   * Updates the item in the database.
   */
  public Variant update(Variant entity) {
    Session session = sessionFactory.getCurrentSession();
    session.update(entity);
    return entity;
  }

  public List<Variant> findAll(long itemId) {
    Session session = sessionFactory.getCurrentSession();
    Query<Variant> query = session.createQuery("from " + Variant.class.getName()+" where itemId=:itemId");
    query.setParameter("itemId", itemId);
    return query.list();
  }
  public List<Variant> findAll() {
    throw new UnsupportedOperationException();
  }

  public void delete(Variant entity) {
    Session session = sessionFactory.getCurrentSession();
    session.delete(entity);
  }

  public Variant findByPrimaryKey(Long id) {
    Session session = sessionFactory.getCurrentSession();
    return session.get(Variant.class, id);
  }

}
