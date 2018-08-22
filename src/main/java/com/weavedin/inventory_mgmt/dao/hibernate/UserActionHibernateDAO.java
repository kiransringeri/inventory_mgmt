package com.weavedin.inventory_mgmt.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
 * This stores the user actions in MySQL table 'user_action'.
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
          + "concat_ws(\" \" , action, concat(group_concat(distinct property SEPARATOR \", \"), ' of'), itemName) "
          + "from user_action ua, user u "
          + "where ua.userId=u.id and actionTime between :startTime and :endTime "
          + "group by u.id, action, itemName, action_datetime\n" + "order by actionTime desc";
  private static final String sql_query_with_userid =
      "select u.id user_id, u.name user_name, date_format(actionTime, \"" + DATE_FORMT_TILL_SECOND
          + "\") action_datetime,  "
          + "concat_ws(\" \" , action, concat(group_concat(distinct property SEPARATOR \", \"), ' of'), itemName) "
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

  @Override
  public void addUserAction(long userId, long branchId, UserActionType actionType,
      String entityName) {
    List<String> properties = new ArrayList<>();
    properties.add(null);
    addUserAction(userId, branchId, actionType, entityName, properties);
  }

  @Override
  public void addUserAction(long userId, long branchId, UserActionType actionType,
      String entityName, String property) {
    List<String> properties = new ArrayList<>();
    properties.add(property);
    addUserAction(userId, branchId, actionType, entityName, properties);
  }

  @Override
  public void addUserAction(long userId, long branchId, UserActionType actionType,
      String entityName, Collection<String> properties) {
    if (properties != null && !properties.isEmpty()) {
      Date currTime = Calendar.getInstance().getTime();
      for (String change : properties) {
        UserAction action = new UserAction();
        action.setAction(actionType.getStorageValue());
        action.setActionTime(currTime);
        action.setUserId(userId);
        action.setBranchId(branchId);
        action.setItemName(entityName);
        action.setProperty(change);
        sessionFactory.getCurrentSession().save(action);
      }
    }
  }

}
