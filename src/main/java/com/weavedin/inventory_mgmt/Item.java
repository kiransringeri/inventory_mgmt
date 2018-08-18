package com.weavedin.inventory_mgmt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing an Item which belongs to a branch of a store.
 * 
 * @author kiransringeri
 *
 */
@Entity
@Table(name = "item")
public class Item {

  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "branchId")
  private long branchId;

  @Column(name = "name")
  private String name;
  
  @Column(name = "brand")
  private String brand;
  
  @Column(name = "category")
  private String category;
  
  @Column(name = "productCode")
  private String productCode;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getBranchId() {
    return branchId;
  }

  public void setBranchId(long branchId) {
    this.branchId = branchId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

}
