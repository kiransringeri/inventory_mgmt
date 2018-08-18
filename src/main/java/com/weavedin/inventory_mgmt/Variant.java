
package com.weavedin.inventory_mgmt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class which represents a variant of an {@link Item}.
 * 
 * @author kiransringeri
 *
 */
@Entity
@Table(name = "variant")
public class Variant {

  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "itemId")
  private long itemId;

  @Column(name = "name")
  private String name;

  @Column(name = "sellingPrice")
  private float sellingPrice;

  @Column(name = "costPrice")
  private float costPrice;

  @Column(name = "quantity")
  private int quantity;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getItemId() {
    return itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(float sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public float getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(float costPrice) {
    this.costPrice = costPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
