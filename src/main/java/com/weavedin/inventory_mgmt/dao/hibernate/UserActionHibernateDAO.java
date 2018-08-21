package com.weavedin.inventory_mgmt.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.weavedin.inventory_mgmt.UserAction;
import com.weavedin.inventory_mgmt.UserActionFeed;
import com.weavedin.inventory_mgmt.dao.UserActionDAO;

/**
 * Hibernate DAO class for managing the storage of {@link UserAction}.
 * 
 * @author kiransringeri
 *
 */
public class UserActionHibernateDAO implements UserActionDAO {

  private static final String DATE_FORMT_TILL_SECOND = "%d-%m-%Y %H:%i:%s";
  public static final String JAVA_DATE_FORMT_TILL_SECOND = "dd-MM-yyyy HH:mm:ss";

  private SessionFactory sessionFactory = null;
  private static final String sql_query_without_userid =
      "select u.id user_id, u.name user_name, date_format(actionTime, \"" + DATE_FORMT_TILL_SECOND
          + "\") action_datetime,  "
          + "concat_ws(\" \" , action, concat(group_concat(distinct property), ' of'), itemName) "
          + "from user_action ua, user u "
          + "where ua.userId=u.id and actionTime between :startTime and :endTime "
          + "group by u.id, action, itemName, action_datetime\n" + "order by actionTime desc";
  private static final String sql_query_with_userid =
      "select u.id user_id, u.name user_name, date_format(actionTime, \"" + DATE_FORMT_TILL_SECOND
          + "\") action_datetime,  "
          + "concat_ws(\" \" , action, concat(group_concat(distinct property), ' of'), itemName) "
          + "from user_action ua, user u "
          + "where ua.userId=u.id and u.id=:userId and actionTime between :startTime and :endTime "
          + "group by u.id, action, itemName, action_datetime\n" + "order by actionTime desc";;

  public UserActionHibernateDAO(SessionFactory sessionFactory) {
    setSessionFactory(sessionFactory);
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public List<UserActionFeed> find(Date from, Date till, Long userId) throws Exception {
    List<UserActionFeed> retList = new ArrayList<>();
    boolean userIdExists = userId != null && userId.longValue() > 0;
    String sqlStr = userIdExists ? sql_query_with_userid : sql_query_without_userid;
    Query<Object[]> query = sessionFactory.getCurrentSession().createSQLQuery(sqlStr);
    query.setParameter("startTime", from);
    query.setParameter("endTime", till);
    if (userIdExists) {
      query.setParameter("userId", userId);
    }
    List<Object[]> results = query.list();
    SimpleDateFormat df = new SimpleDateFormat(JAVA_DATE_FORMT_TILL_SECOND);
    for (Object[] rowData : results) {
      UserActionFeed feed = new UserActionFeed();
      retList.add(feed);
      feed.setUserId(((Number) rowData[0]).longValue());
      feed.setUserName((String) rowData[1]);
      String dateStr = (String) rowData[2];
      feed.setTime(df.parse(dateStr));
      feed.setActions((String) rowData[3]);
    }
    return retList;
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
