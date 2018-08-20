package com.weavedin.inventory_mgmt.dao.hibernate;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.weavedin.inventory_mgmt.Item;
import com.weavedin.inventory_mgmt.UserAction;
import com.weavedin.inventory_mgmt.dao.UserActionDAO;

/**
 * Hibernate DAO class for managing the storage of {@link Item}.
 * @author kiransringeri
 *
 */
public class UserActionHibernateDAO implements UserActionDAO {

  private SessionFactory sessionFactory = null;

  public UserActionHibernateDAO(SessionFactory sessionFactory) {
    setSessionFactory(sessionFactory);
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * Saves the new item to the database.
   */
  public UserAction save(UserAction entity) {
    Session session = sessionFactory.getCurrentSession();
    session.save(entity);
    return entity;
  }

  /**
   * Updates the item in the database.
   */
  public UserAction update(UserAction entity) {
    throw new UnsupportedOperationException();
  }

  public List<UserAction> findAll() {
    throw new UnsupportedOperationException();
  }

  public void delete(UserAction entity) {
    throw new UnsupportedOperationException();
  }

  public UserAction findByPrimaryKey(Long id) {
    throw new UnsupportedOperationException();
  }

}
