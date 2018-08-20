package com.weavedin.inventory_mgmt.api;

import java.util.Date;
import java.util.List;
import com.weavedin.inventory_mgmt.Item;
import com.weavedin.inventory_mgmt.UserActionFeed;
import com.weavedin.inventory_mgmt.Variant;

/**
 * Interface for functionalities.
 * 
 * @author kiransringeri
 *
 */
public interface InventoryManagementAPI {
  public APIResponse<Item> saveItem(long userId, long branchId, Item item);
  public APIResponse<Item> updateItem(long userId, long branchId, Item item);
  public APIResponse<Boolean> deleteItem(long userId, long branchId, Item item);
  public APIResponse<Variant> saveVariant(long userId, long branchId, long itemId, Variant variant);
  public APIResponse<Variant> updateVariant(long userId, long branchId, long itemId, Variant variant);
  public APIResponse<Boolean> deleteVariant(long userId, long branchId, Variant variant);
  public APIResponse<List<UserActionFeed>> getUserActions(Date from, Date till, long userId);
}