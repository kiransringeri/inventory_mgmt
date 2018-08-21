package com.weavedin.inventory_mgmt.dao;

import java.util.Date;
import java.util.List;
import com.weavedin.inventory_mgmt.UserAction;
import com.weavedin.inventory_mgmt.UserActionFeed;

/**
 * DAO for {@link UserAction}
 * @author kiransringeri
 *
 */
public interface UserActionDAO extends DataDAO<UserAction, Long> {
  public List<UserActionFeed> find(Date from, Date till, Long userId) throws Exception;
}
