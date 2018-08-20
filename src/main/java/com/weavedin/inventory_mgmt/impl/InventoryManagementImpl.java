package com.weavedin.inventory_mgmt.impl;

import java.util.Date;
import java.util.List;
import javax.transaction.Transaction;
import com.weavedin.inventory_mgmt.Item;
import com.weavedin.inventory_mgmt.UserActionFeed;
import com.weavedin.inventory_mgmt.Variant;
import com.weavedin.inventory_mgmt.api.APIResponse;
import com.weavedin.inventory_mgmt.api.InventoryManagementAPI;

public class InventoryManagementImpl implements InventoryManagementAPI {

  @Override
  public APIResponse<Item> saveItem(Item item) {
    APIResponse<Item> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      
    }catch(Throwable th) {
      retObj.setError(true);
      retObj.setException(th);
      try {
        txn.rollback();
      } catch (Exception e) {
        
      }
    }
    return retObj;
  }

  @Override
  public APIResponse<Item> updateItem(Item item) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteItem(Item item) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public APIResponse<Variant> saveVariant(Variant variant) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public APIResponse<Variant> updateVariant(Variant variant) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteVariant(Variant variant) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public APIResponse<List<UserActionFeed>> getUserActions(Date from, Date till, long userId) {
    // TODO Auto-generated method stub
    return null;
  }


}
