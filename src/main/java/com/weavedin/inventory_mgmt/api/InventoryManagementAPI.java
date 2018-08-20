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
  public APIResponse<Item> saveItem(Item item);
  public APIResponse<Item> updateItem(Item item);
  public void deleteItem(Item item);
  public APIResponse<Variant> saveVariant(Variant variant);
  public APIResponse<Variant> updateVariant(Variant variant);
  public void deleteVariant(Variant variant);
  public APIResponse<List<UserActionFeed>> getUserActions(Date from, Date till, long userId);
}
