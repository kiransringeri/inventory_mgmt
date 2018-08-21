package com.weavedin.inventory_mgmt.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import com.weavedin.inventory_mgmt.UserActionFeed;
import com.weavedin.inventory_mgmt.dao.DataDAOFactory;
import com.weavedin.inventory_mgmt.dao.UserActionDAO;

public class UserActionHibernateDAOTest {

  @Test
  void Should_GetFeedsForAllUser() throws Exception{
    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    UserActionDAO dao = daoFact.getUserActionDAO();
    Transaction txn = fact.getCurrentSession().beginTransaction();
//    dd-MM-yyyy HH:mm:ss
    Date fromDate = new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND).parse("07-07-2018 09:00:00");
    Date tillDate = new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND).parse("07-10-2018 09:00:00");;
    Long userId = null;
    List<UserActionFeed> feeds = dao.find(fromDate, tillDate, userId);
    txn.rollback();
    assertEquals(true, feeds.size() > 0);
  }
  
  @Test
  void Should_GetFeedsForSpecificUser() throws Exception{
    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    UserActionDAO dao = daoFact.getUserActionDAO();
    Transaction txn = fact.getCurrentSession().beginTransaction();
//    dd-MM-yyyy HH:mm:ss
    Date fromDate = new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND).parse("07-07-2018 09:00:00");
    Date tillDate = new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND).parse("07-10-2018 09:00:00");;
    Long userId = 1L;
    List<UserActionFeed> feeds = dao.find(fromDate, tillDate, userId);
    txn.rollback();
    assertEquals(true, feeds.size() > 0);
  }
  
  @Test
  void Should_GetNothing_When_NoActionsExistForDateRange() throws Exception{
    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    UserActionDAO dao = daoFact.getUserActionDAO();
    Transaction txn = fact.getCurrentSession().beginTransaction();
//    dd-MM-yyyy HH:mm:ss
    Date fromDate = new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND).parse("07-07-2018 09:00:00");
    Date tillDate = new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND).parse("07-10-2017 09:00:00");;
    Long userId = null;
    List<UserActionFeed> feeds = dao.find(fromDate, tillDate, userId);
    txn.rollback();
    assertEquals(0, feeds.size());
  }
  
  @Test
  void Should_GetNothing_When_NoActionsExistForUser() throws Exception{
    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    UserActionDAO dao = daoFact.getUserActionDAO();
    Transaction txn = fact.getCurrentSession().beginTransaction();
//    dd-MM-yyyy HH:mm:ss
    Date fromDate = new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND).parse("07-07-2018 09:00:00");
    Date tillDate = new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND).parse("07-10-2018 09:00:00");;
    Long userId = 100L;
    List<UserActionFeed> feeds = dao.find(fromDate, tillDate, userId);
    txn.rollback();
    assertEquals(0, feeds.size());
  }
}
