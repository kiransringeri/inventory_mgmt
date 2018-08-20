package com.weavedin.inventory_mgmt.dao;

import java.net.URL;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Class to get the Hibernate Session.
 * 
 * @author kiransringeri
 *
 */
public class SessionManager {

  private static SessionFactory sessionFactory = null;
  private static final String CONFIG_FILE = "com/weavedin/inventory_mgmt/dao/hibernate.cfg.xml";

  /**
   * Returns the {@link Session} object. Remember to close it once work is done.
   * @return the {@link Session} object
   */
  public static Session getSession() {
    if (sessionFactory == null) {
      initSessionFactory();
    }
    return sessionFactory.openSession();
  }

  private static synchronized void initSessionFactory() {
    if (sessionFactory != null) {
      return;
    }
    Configuration config = new Configuration();
    URL url = SessionManager.class.getClassLoader().getResource(CONFIG_FILE);
    config = config.configure(url);
    sessionFactory = config.buildSessionFactory();
  }
}
