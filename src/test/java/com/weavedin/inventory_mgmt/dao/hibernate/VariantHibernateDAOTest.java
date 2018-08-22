package com.weavedin.inventory_mgmt.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import com.weavedin.inventory_mgmt.Item;
import com.weavedin.inventory_mgmt.Variant;
import com.weavedin.inventory_mgmt.dao.DataDAOFactory;
import com.weavedin.inventory_mgmt.dao.ItemDAO;
import com.weavedin.inventory_mgmt.dao.VariantDAO;

class VariantHibernateDAOTest {

  private Item getNewItem() {
    Item item = new Item();
    long branchId = 1;
    item.setBranchId(branchId);
    item.setBrand("Brand 1");
    item.setCategory("Cate 1");
    item.setName("Item "+ System.currentTimeMillis());
    item.setProductCode("PCode 1");
    return item;
  }

  private Variant getNewVariant(long itemId) {
    Variant var = new Variant();
    var.setCostPrice(1234.0F);
    var.setItemId(itemId);
    var.setName("Variant " + System.currentTimeMillis());
    var.setQuantity(2);
    var.setSellingPrice(1300.0F);
    return var;
  }

  @Test
  void Should_SaveNewVariant() {
    Item item = getNewItem();

    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    ItemDAO itemDao = daoFact.getItemDAO();
    VariantDAO varDao = daoFact.getVariantDAO();

    Transaction txn = fact.getCurrentSession().beginTransaction();

    itemDao.save(item);
    Variant var = getNewVariant(item.getId());
    varDao.save(var);

    assertEquals(true, var.getId() > 0);
    txn.commit();

    txn = fact.getCurrentSession().beginTransaction();
    Variant varFromDB = varDao.findByPrimaryKey(var.getId());
    assertEquals(var.getCostPrice(), varFromDB.getCostPrice());
    assertEquals(var.getItemId(), varFromDB.getItemId());
    assertEquals(var.getName(), varFromDB.getName());
    assertEquals(var.getQuantity(), varFromDB.getQuantity());
    assertEquals(var.getSellingPrice(), varFromDB.getSellingPrice());
    varDao.delete(varFromDB);
    itemDao.delete(item);
    txn.commit();
  }

  @Test
  void Should_UpdateValues() {
    Item item = getNewItem();

    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    ItemDAO itemDao = daoFact.getItemDAO();
    VariantDAO varDao = daoFact.getVariantDAO();

    Transaction txn = fact.getCurrentSession().beginTransaction();
    itemDao.save(item);
    Variant var = getNewVariant(item.getId());
    varDao.save(var);
    txn.commit();

    txn = fact.getCurrentSession().beginTransaction();
    Variant varToUpdate = varDao.findByPrimaryKey(var.getId());
    varToUpdate.setCostPrice(varToUpdate.getCostPrice() + 20);
    varToUpdate.setName(varToUpdate.getName() + "_updated");
    varToUpdate.setQuantity(varToUpdate.getQuantity() + 2);
    varToUpdate.setSellingPrice(varToUpdate.getSellingPrice() - 1);
    varDao.save(varToUpdate);
    txn.commit();

    txn = fact.getCurrentSession().beginTransaction();
    Variant varFromDB = varDao.findByPrimaryKey(var.getId());
    assertEquals(varToUpdate.getCostPrice(), varFromDB.getCostPrice());
    assertEquals(varToUpdate.getName(), varFromDB.getName());
    assertEquals(varToUpdate.getQuantity(), varFromDB.getQuantity());
    assertEquals(varToUpdate.getSellingPrice(), varFromDB.getSellingPrice());
    varDao.delete(varFromDB);
    itemDao.delete(item);
    txn.commit();
  }
  
  @Test
  void Should_AddNewPropertiesToVariant() {
    Item item = getNewItem();

    SessionFactory fact = FactoryUtil.getSessionFactory();
    DataDAOFactory daoFact = new DataDAOFactory();
    ItemDAO itemDao = daoFact.getItemDAO();
    VariantDAO varDao = daoFact.getVariantDAO();

    Transaction txn = fact.getCurrentSession().beginTransaction();

    itemDao.save(item);
    Variant var = getNewVariant(item.getId());
    String propName = "Prop 1";
    var.addProperty(propName, "Value 1");
    varDao.save(var);
    txn.commit();

    txn = fact.getCurrentSession().beginTransaction();
    Variant varFromDB = varDao.findByPrimaryKey(var.getId());
    assertEquals(var.getProperty(propName), varFromDB.getProperty(propName));
    varDao.delete(varFromDB);
    itemDao.delete(item);
    txn.commit();
  }
}
