package com.weavedin.inventory_mgmt;

import java.util.Date;

/**
 * Class which is used for user action feed.
 * 
 * @author kiransringeri
 *
 */
public class UserActionFeed {
  private Date time;
  private long userId;
  private String userName;
  private String actions;
  public Date getTime() {
    return time;
  }
  public void setTime(Date time) {
    this.time = time;
  }
  public long getUserId() {
    return userId;
  }
  public void setUserId(long userId) {
    this.userId = userId;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getActions() {
    return actions;
  }
  public void setActions(String actions) {
    this.actions = actions;
  }
}
