package com.weavedin.inventory_mgmt.dao.hibernate;

import java.net.URL;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Class to get the Hibernate Session Factory.
 * 
 * @author kiransringeri
 *
 */
public class FactoryUtil {

  private static SessionFactory sessionFactory = null;
  private static final String CONFIG_FILE =
      "com/weavedin/inventory_mgmt/dao/hibernate/hibernate.cfg.xml";

  /**
   * Returns the session factory
   * 
   * @return
   */
  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      initSessionFactory();
    }
    return sessionFactory;
  }

  private static synchronized void initSessionFactory() {
    if (sessionFactory != null) {
      return;
    }
    Configuration config = new Configuration();
    URL url = FactoryUtil.class.getClassLoader().getResource(CONFIG_FILE);
    config = config.configure(url);
    sessionFactory = config.buildSessionFactory();
  }
}
