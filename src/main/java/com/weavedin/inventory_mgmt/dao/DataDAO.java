package com.weavedin.inventory_mgmt.dao;

import java.io.Serializable;
import java.util.List;

/**
 * A generic DAO interface which can be used for any entoty DAO.
 * 
 * @author kiransringeri
 *
 * @param <T>
 * @param <ID>
 */
public interface DataDAO <T, ID extends Serializable> {
  public T save(T entity);
  public T update(T entity);
  public List<T> findAll();
  public void delete(T entity);
  public T findByPrimaryKey(ID id);
}
