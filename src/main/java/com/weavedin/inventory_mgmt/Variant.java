
package com.weavedin.inventory_mgmt;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @ElementCollection
  @MapKeyColumn(name = "property")
  @Column(name = "value")
  @CollectionTable(name = "variant_property", joinColumns = @JoinColumn(name = "variantId"))
  private Map<String, String> properties = new HashMap<String, String>();

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

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public void addProperty(String property, String value) {
    if (value == null) {
      this.properties.remove(property);
    } else {
      this.properties.put(property, value);
    }
  }

  public String getProperty(String property) {
    return properties.get(property);
  }
}
