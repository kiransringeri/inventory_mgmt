package com.weavedin.inventory_mgmt.dao.hibernate;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.weavedin.inventory_mgmt.Item;
import com.weavedin.inventory_mgmt.dao.ItemDAO;

/**
 * Hibernate DAO class for managing the storage of {@link Item}.
 * @author kiransringeri
 *
 */
public class ItemHibernateDAO implements ItemDAO {

  private SessionFactory sessionFactory = null;

  public ItemHibernateDAO(SessionFactory sessionFactory) {
    setSessionFactory(sessionFactory);
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Saves the new item to the database.
   */
  public Item save(Item entity) {
    Session session = sessionFactory.getCurrentSession();
    session.save(entity);
    return entity;
  }

  /**
   * Updates the item in the database.
   */
  public Item update(Item entity) {
    Session session = sessionFactory.getCurrentSession();
    session.update(entity);
    return entity;
  }

  public List<Item> findAll() {
    Session session = sessionFactory.getCurrentSession();
    Query<Item> query = session.createQuery("from " + Item.class.getName());
    return query.list();
  }

  public void delete(Item entity) {
    Session session = sessionFactory.getCurrentSession();
    session.delete(entity);
  }

  public Item findByPrimaryKey(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Item item = session.get(Item.class, id);
    return item;
  }

}
