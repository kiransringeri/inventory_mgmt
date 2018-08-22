package com.weavedin.inventory_mgmt.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import com.weavedin.inventory_mgmt.Item;
import com.weavedin.inventory_mgmt.dao.DataDAOFactory;
import com.weavedin.inventory_mgmt.dao.ItemDAO;

class ItemHibernateDAOTest {

  private Item getNewItem() {
    Item item = new Item();
    long branchId = 1;
    item.setBranchId(branchId);
    item.setBrand("Brand 1");
    item.setCategory("Cate 1");
    item.setName("Item " + System.currentTimeMillis());
    item.setProductCode("PCode 1");
    return item;
  }

  @Test
  void Should_SaveNewItem() {
    Item item = getNewItem();
    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    ItemDAO dao = daoFact.getItemDAO();

    Transaction txn = fact.getCurrentSession().beginTransaction();
    Item savedItem = dao.save(item);
    assertEquals(true, savedItem.getId() > 0);
    txn.commit();

    txn = fact.getCurrentSession().beginTransaction();
    Item itemFromDB = dao.findByPrimaryKey(savedItem.getId());
    assertEquals(item.getBranchId(), itemFromDB.getBranchId());
    assertEquals(item.getBrand(), itemFromDB.getBrand());
    assertEquals(item.getCategory(), itemFromDB.getCategory());
    assertEquals(item.getName(), itemFromDB.getName());
    assertEquals(item.getProductCode(), itemFromDB.getProductCode());

    dao.delete(itemFromDB);
    txn.commit();
  }

  @Test
  void Should_UpdateValues() {
    // Save new Item
    Item item = getNewItem();
    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    ItemDAO dao = daoFact.getItemDAO();

    Transaction txn = fact.getCurrentSession().beginTransaction();
    Item savedItem = dao.save(item);
    assertEquals(true, savedItem.getId() > 0);
    txn.commit();
    // Update the item
    txn = fact.getCurrentSession().beginTransaction();
    Item updatedItem = dao.findByPrimaryKey(savedItem.getId());
    // updatedItem.setBrand(updatedItem.getBrand()+"_updated");
    updatedItem.setCategory(updatedItem.getCategory() + "_updated");
    updatedItem.setName(updatedItem.getName() + "_updated");
    updatedItem.setProductCode(updatedItem.getProductCode() + "_updated");
    dao.update(updatedItem);
    txn.commit();

    // Check the updated values
    txn = fact.getCurrentSession().beginTransaction();
    Item itemFromDB = dao.findByPrimaryKey(savedItem.getId());
    assertEquals(updatedItem.getBrand(), itemFromDB.getBrand());
    assertEquals(updatedItem.getCategory(), itemFromDB.getCategory());
    assertEquals(updatedItem.getName(), itemFromDB.getName());
    assertEquals(updatedItem.getProductCode(), itemFromDB.getProductCode());

    dao.delete(itemFromDB);
    txn.commit();
  }

  @Test
  void Should_SaveRetriveAndDeleteItem() {
    Item item = getNewItem();
    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    ItemDAO dao = daoFact.getItemDAO();

    // Add item
    Transaction txn = fact.getCurrentSession().beginTransaction();
    dao.save(item);
    txn.commit();
    assertEquals(true, item.getId() > 0);

    // Delete item
    txn = fact.getCurrentSession().beginTransaction();
    Item itemFromDB = dao.findByPrimaryKey(item.getId());
    assertEquals(item.getId(), itemFromDB.getId());
    dao.delete(itemFromDB);
    txn.commit();

    // Check item
    txn = fact.getCurrentSession().beginTransaction();
    Item itemDeleted = dao.findByPrimaryKey(itemFromDB.getId());
    assertNull(itemDeleted);
    txn.rollback();
  }
}
