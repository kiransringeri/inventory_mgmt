package com.weavedin.inventory_mgmt.dao;

import java.util.List;
import com.weavedin.inventory_mgmt.Variant;

/**
 * DAO for {@link Variant}.
 * @author kiransringeri
 *
 */
public interface VariantDAO extends DataDAO<Variant, Long> {
  public List<Variant> findAll(long itemId);
}
