package com.weavedin.inventory_mgmt;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "variant")

public class UserAction {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  @Column(name = "userId")
  private long userId;

  @Column(name = "actionTime")
  private Date actionTime;

  @Column(name = "action")
  private String action;

  @Column(name = "property")
  private String property;

  @Column(name = "itemName")
  private String itemName;
  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public long getUserId() {
    return userId;
  }
  public void setUserId(long userId) {
    this.userId = userId;
  }
  public Date getActionTime() {
    return actionTime;
  }
  public void setActionTime(Date actionTime) {
    this.actionTime = actionTime;
  }
  public String getAction() {
    return action;
  }
  public void setAction(String action) {
    this.action = action;
  }
  public String getProperty() {
    return property;
  }
  public void setProperty(String property) {
    this.property = property;
  }
  public String getItemName() {
    return itemName;
  }
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }
  
}
