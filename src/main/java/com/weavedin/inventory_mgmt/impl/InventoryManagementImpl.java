package com.weavedin.inventory_mgmt.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.weavedin.inventory_mgmt.Item;
import com.weavedin.inventory_mgmt.UserAction;
import com.weavedin.inventory_mgmt.UserActionFeed;
import com.weavedin.inventory_mgmt.Variant;
import com.weavedin.inventory_mgmt.api.APIResponse;
import com.weavedin.inventory_mgmt.api.InventoryManagementAPI;
import com.weavedin.inventory_mgmt.dao.DataDAOFactory;
import com.weavedin.inventory_mgmt.dao.hibernate.FactoryUtil;

public class InventoryManagementImpl implements InventoryManagementAPI {

  @Override
  public APIResponse<Item> saveItem(long userId, long branchId, Item item) {
    APIResponse<Item> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      UserAction action = new UserAction();
      action.setAction("Added");
      action.setActionTime(Calendar.getInstance().getTime());
      action.setUserId(userId);
      action.setBranchId(branchId);
      action.setItemName("item "+item.getName());
      
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      
      txn = sessionFactry.getCurrentSession().beginTransaction();
      daoFactry.getItemDAO().save(item);
      daoFactry.getUserActionDAO().save(action);
      txn.commit();
      
      retObj.setReturnData(item);
      
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
  public APIResponse<Item> updateItem(long userId, long branchId, Item item) {
    APIResponse<Item> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      txn = sessionFactry.getCurrentSession().beginTransaction();
      
      Item itemInDB = daoFactry.getItemDAO().findByPrimaryKey(item.getId());
      String changes = updateItemData(itemInDB, item);
      daoFactry.getItemDAO().update(itemInDB);

      if(changes != null) {
        UserAction action = new UserAction();
        action.setAction("Edited");
        action.setActionTime(Calendar.getInstance().getTime());
        action.setUserId(userId);
        action.setBranchId(branchId);
        action.setItemName("item "+item.getName());
        action.setProperty(changes);
        daoFactry.getUserActionDAO().save(action);
      }
      txn.commit();
      retObj.setReturnData(itemInDB);
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

  private String updateItemData(Item itemInDB, Item updatedItem) {
    List<String> updatedProps = new ArrayList<>();
    if(!equals(itemInDB.getBrand(), updatedItem.getBrand())){
      itemInDB.setBrand(updatedItem.getBrand());
      updatedProps.add("brand");
    }
    if(!equals(itemInDB.getCategory(), updatedItem.getCategory())){
      itemInDB.setCategory(updatedItem.getCategory());
      updatedProps.add("category");
    }
    if(!equals(itemInDB.getName(), updatedItem.getName())){
      itemInDB.setName(updatedItem.getName());
      updatedProps.add("name");
    }
    if(!equals(itemInDB.getProductCode(), updatedItem.getProductCode())){
      itemInDB.setProductCode(updatedItem.getProductCode());
      updatedProps.add("product code");
    }
    String str = updatedProps.isEmpty() ? null : updatedProps.stream().collect(Collectors.joining(","));
    return str;
  }
  
  private String updateVariantData(Variant variantInDB, Variant updatedVariant) {
    List<String> updatedProps = new ArrayList<>();
    if(!equals(variantInDB.getName(), updatedVariant.getName())){
      variantInDB.setName(updatedVariant.getName());
      updatedProps.add("name");
    }
    if(variantInDB.getCostPrice() != updatedVariant.getCostPrice()) {
      variantInDB.setCostPrice(updatedVariant.getCostPrice());
      updatedProps.add("cost price");
    }
    if(variantInDB.getSellingPrice() != updatedVariant.getSellingPrice()) {
      variantInDB.setSellingPrice(updatedVariant.getSellingPrice());
      updatedProps.add("selling price");
    }
    if(variantInDB.getQuantity() != updatedVariant.getQuantity()) {
      variantInDB.setQuantity(updatedVariant.getQuantity());
      updatedProps.add("quantity");
    }
    Map<String, String> otherPropsInDB = variantInDB.getProperties() == null ? new HashMap<>() : variantInDB.getProperties();
    Map<String, String> otherPropsUpdated = updatedVariant.getProperties() == null ? new HashMap<>() : new HashMap<>(updatedVariant.getProperties());
    for(String prop : otherPropsInDB.keySet()) {
      String valInDB = otherPropsInDB.get(prop);
      String valUpdated = otherPropsUpdated.remove(prop);
      if(!equals(valInDB, valUpdated)) {
        variantInDB.addProperty(prop, valUpdated);
        updatedProps.add(prop);
      }
    }
    for(String prop : otherPropsUpdated.keySet()) {
      String valUpdated = otherPropsUpdated.remove(prop);
      variantInDB.addProperty(prop, valUpdated);
      updatedProps.add(prop);
    }
    
    String str = updatedProps.isEmpty() ? null : updatedProps.stream().collect(Collectors.joining(","));
    return str;
  }
  
  private boolean equals(Object str1, Object str2) {
    if(str1 == null && str2 == null) {
      return true;
    }else if(str1 == null || str2 == null) {
      return false;
    }else {
      return str1.equals(str2);
    }
  }
  
  @Override
  public APIResponse<Boolean> deleteItem(long userId, long branchId, long itemId) {
    APIResponse<Boolean> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      txn = sessionFactry.getCurrentSession().beginTransaction();
      
      Item item = daoFactry.getItemDAO().findByPrimaryKey(itemId);
      daoFactry.getItemDAO().delete(item);
      
      UserAction action = new UserAction();
      action.setAction("Deleted");
      action.setActionTime(Calendar.getInstance().getTime());
      action.setUserId(userId);
      action.setBranchId(branchId);
      action.setItemName("item "+item.getName());
      daoFactry.getUserActionDAO().save(action);

      txn.commit();
      retObj.setReturnData(true);
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
  public APIResponse<Variant> saveVariant(long userId, long branchId, long itemId,
      Variant variant) {
    APIResponse<Variant> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      txn = sessionFactry.getCurrentSession().beginTransaction();

      String itemName = getItemName(sessionFactry, variant.getItemId());
      
      UserAction action = new UserAction();
      action.setAction("Added");
      action.setActionTime(Calendar.getInstance().getTime());
      action.setUserId(userId);
      action.setBranchId(branchId);
      action.setItemName("variant "+variant.getName()+" of item "+itemName);
      
      daoFactry.getVariantDAO().save(variant);
      daoFactry.getUserActionDAO().save(action);
      txn.commit();
      
      retObj.setReturnData(variant == null ? null : (Variant)variant.clone());
      
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
  public APIResponse<Variant> updateVariant(long userId, long branchId, long itemId,
      Variant variant) {
    APIResponse<Variant> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      txn = sessionFactry.getCurrentSession().beginTransaction();
      
      String itemName = getItemName(sessionFactry, variant.getItemId());
      
      Variant variantInDB = daoFactry.getVariantDAO().findByPrimaryKey(variant.getId());
      
      String changes = updateVariantData(variantInDB, variant);
      daoFactry.getVariantDAO().update(variantInDB);

      if(changes != null) {
        UserAction action = new UserAction();
        action.setAction("Edited");
        action.setActionTime(Calendar.getInstance().getTime());
        action.setUserId(userId);
        action.setBranchId(branchId);
        action.setItemName("variant "+variant.getName()+" of item "+itemName);
        action.setProperty(changes);
        daoFactry.getUserActionDAO().save(action);
      }
      txn.commit();
      retObj.setReturnData(variantInDB == null ? null : (Variant)variantInDB.clone());
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

  private String getItemName(SessionFactory sessionFactry, long itemId) {
    Query<String> query = sessionFactry.getCurrentSession().createSQLQuery("select name from item where id=:itemId");
    query.setParameter("itemId", itemId);
    String itemName = query.uniqueResult();
    return itemName;
  }
  
  @Override
  public APIResponse<Boolean> deleteVariant(long userId, long branchId, long variantId) {
    APIResponse<Boolean> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      txn = sessionFactry.getCurrentSession().beginTransaction();
      
      Variant variant = daoFactry.getVariantDAO().findByPrimaryKey(variantId);
      String itemName = getItemName(sessionFactry, variant.getItemId());
      
      daoFactry.getVariantDAO().delete(variant);
      
      UserAction action = new UserAction();
      action.setAction("Deleted");
      action.setActionTime(Calendar.getInstance().getTime());
      action.setUserId(userId);
      action.setBranchId(branchId);
      action.setItemName("variant "+variant.getName()+" of item "+itemName);
      daoFactry.getUserActionDAO().save(action);

      txn.commit();
      retObj.setReturnData(true);
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
  public APIResponse<List<UserActionFeed>> getUserActions(Date from, Date till, Long userId) {
    APIResponse<List<UserActionFeed>> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      if(from == null || till == null) {
        return null;
      }
      
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      
      txn = sessionFactry.getCurrentSession().beginTransaction();
      List<UserActionFeed> items = daoFactry.getUserActionDAO().find(from, till, userId);
      txn.rollback();
      
      retObj.setReturnData(items);
      
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
  public APIResponse<List<Item>> getItems(long userId, long branchId) {
    APIResponse<List<Item>> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      
      txn = sessionFactry.getCurrentSession().beginTransaction();
      List<Item> items = daoFactry.getItemDAO().findAll();
      txn.rollback();
      
      retObj.setReturnData(items);
      
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
  public APIResponse<Item> getItem(long userId, long branchId, long itemId) {
    APIResponse<Item> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      
      txn = sessionFactry.getCurrentSession().beginTransaction();
      Item item = daoFactry.getItemDAO().findByPrimaryKey(itemId);
      txn.rollback();
      
      retObj.setReturnData(item);
      
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
  public APIResponse<List<Variant>> getVariants(long userId, long branchId, long itemId) {
    APIResponse<List<Variant>> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      
      txn = sessionFactry.getCurrentSession().beginTransaction();
      List<Variant> items = daoFactry.getVariantDAO().findAll(itemId);
      txn.rollback();
      
      List<Variant> itemClones = null;
      if(items != null) {
        itemClones = new ArrayList<>();
        for(Variant var : items) {
          itemClones.add((Variant)var.clone());
        }
      }
      retObj.setReturnData(itemClones);
      
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
  public APIResponse<Variant> getVariant(long userId, long branchId, long variantId) {
    APIResponse<Variant> retObj = new APIResponse<>();
    Transaction txn = null;
    try {
      DataDAOFactory daoFactry = new DataDAOFactory();
      SessionFactory sessionFactry = FactoryUtil.getSessionFactory();
      
      txn = sessionFactry.getCurrentSession().beginTransaction();
      Variant variant = daoFactry.getVariantDAO().findByPrimaryKey(variantId);
      txn.rollback();
      
      retObj.setReturnData(variant == null ? null : (Variant)variant.clone());
      
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

}
