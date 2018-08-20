package com.weavedin.inventory_mgmt.dao;

import org.hibernate.SessionFactory;
import com.weavedin.inventory_mgmt.dao.hibernate.FactoryUtil;
import com.weavedin.inventory_mgmt.dao.hibernate.ItemHibernateDAO;
import com.weavedin.inventory_mgmt.dao.hibernate.UserActionHibernateDAO;
import com.weavedin.inventory_mgmt.dao.hibernate.VariantHibernateDAO;

public class DataDAOFactory {
  private SessionFactory factory = null;

  public DataDAOFactory() {
    factory = FactoryUtil.getSessionFactory();
  }

  public ItemDAO getItemDAO() {
    return new ItemHibernateDAO(factory);
  }

  public VariantDAO getVariantDAO() {
    return new VariantHibernateDAO(factory);
  }
  
  public UserActionDAO getUserActionDAO() {
    return new UserActionHibernateDAO(factory);
  }
}
