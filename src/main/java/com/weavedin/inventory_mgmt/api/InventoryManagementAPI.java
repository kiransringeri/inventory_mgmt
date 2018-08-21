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
  /**
   * Save a new {@link Item}.
   * @param userId id of the user
   * @param branchId id of the branch in which this {@link Item} is being added.
   * @param item the {@link Item} to be added.
   * @return the {@link Item} wrapped in {@link APIResponse}
   */
  public APIResponse<Item> saveItem(long userId, long branchId, Item item);
  /**
   * Updates an existing {@link Item}.
   * @param userId id of the user
   * @param branchId id of the branch in which this {@link} Item is being updated.
   * @param item the {@link Item} to be updated.
   * @return the {@link Item} wrapped in {@link APIResponse}
   */
  public APIResponse<Item> updateItem(long userId, long branchId, Item item);
  /**
   * Deleted an {@link Item}.
   * @param userId userId id of the user.
   * @param branchId the id of the branch.
   * @param itemId
   * @return true/false wrapped in {@link APIResponse}. True if deleted successfully, false otherwise.
   */
  public APIResponse<Boolean> deleteItem(long userId, long branchId, long itemId);
  /**
   * Returns all the items in this branch.
   * @param userId userId id of the user.
   * @param branchId the id of the branch.
   * @return {@link List} of {@link Item} objects wrapped in {@link APIResponse}
   */
  public APIResponse<List<Item>> getItems(long userId, long branchId);
  /**
   * Returns a specific {@link Item} for the specified itemId.
   * @param userId userId id of the user.
   * @param branchId the id of the branch.
   * @param itemId id of the {@link Item} to be retrieved.
   * @return the {@link Item} object wrapped in {@link APIResponse}
   */
  public APIResponse<Item> getItem(long userId, long branchId, long itemId);
  /**
   * Saves a new {@link Variant} of a item.
   * @param userId userId id of the user.
   * @param branchId the id of the branch.
   * @param itemId the id of the {@link Item}
   * @param variant the {@link Variant} to be saved.
   * @return {@link Variant} wrapped in {@link APIResponse}.
   */
  public APIResponse<Variant> saveVariant(long userId, long branchId, long itemId, Variant variant);
  /**
   * Updates a {@link Variant} object.
   * @param userId userId id of the user.
   * @param branchId the id of the branch.
   * @param itemId id of the {@link Item}
   * @param variant the {@link Variant} to be updated.
   * @return {@link Variant} wrapped in {@link APIResponse}.
   */
  public APIResponse<Variant> updateVariant(long userId, long branchId, long itemId, Variant variant);
  /**
   * Deleted the {@link Variant} specified by the id.
   * @param userId userId id of the user.
   * @param branchId the id of the branch.
   * @param variantId id of the {@link Variant} to be deleted.
   * @return true/false wrapped in {@link APIResponse}. True if deleted successfully, false otherwise.
   */
  public APIResponse<Boolean> deleteVariant(long userId, long branchId, long variantId);
  /**
   * Returns all {@link Variant} objects for the given {@link Item}.
   * @param userId userId id of the user.
   * @param branchId the id of the branch.
   * @param itemId id of the {@link Item} for which we need the {@link Variant} objects.
   * @return {@link List} of {@link Variant} wrapped in {@link APIResponse}
   */
  public APIResponse<List<Variant>> getVariants(long userId, long branchId, long itemId);
  /**
   * Returns a {@link Variant} for the specified variantId.
   * @param userId userId id of the user.
   * @param branchId the id of the branch.
   * @param variantId id of the {@link Variant}.
   * @return {@link Variant} object wrapped in {@link APIResponse}
   */
  public APIResponse<Variant> getVariant(long userId, long branchId, long variantId);
  /**
   * Returns the user actions feed for the specified date range and user.
   * 
   * @param from the from date
   * @param till the till date
   * @param userId optional. If specified only user actions of this user are returned.
   * @return {@link List} of {@link UserActionFeed} objects wrapped in {@link APIResponse}
   */
  public APIResponse<List<UserActionFeed>> getUserActions(Date from, Date till, Long userId);
}
