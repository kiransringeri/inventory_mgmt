package com.weavedin.inventory_mgmt.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.weavedin.inventory_mgmt.UserAction;
import com.weavedin.inventory_mgmt.UserActionFeed;

/**
 * DAO for {@link UserAction}
 * 
 * @author kiransringeri
 *
 */
public interface UserActionDAO {
  public static enum UserActionType {
    ADD("Added"), EDIT("Edited"), DELETE("Deleted");
    private String storageValue;

    private UserActionType(String storageValue) {
      this.storageValue = storageValue;
    }

    public String getStorageValue() {
      return storageValue;
    }
  }

  public List<UserActionFeed> find(Date from, Date till, Long userId) throws Exception;

  public void addUserAction(long userId, long branchId, UserActionType actionType,
      String entityName);
  
  public void addUserAction(long userId, long branchId, UserActionType actionType,
      String entityName, String property);

  public void addUserAction(long userId, long branchId, UserActionType actionType,
      String entityName, Collection<String> properties);
}
