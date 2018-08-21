## Inventory Management ##
  
### Functionalities ###
  * API to add/edit/delete Items
  * API to add/edit/delete Variants of Items
  * API to fetch user actions as feed
  
    
### Entity Fields ###
 #### Item ###
  * branchId - id of the store branch
  * name - name of the item
  * brand - brand name of the item
  * category - category of the item
  * productCode - product code
 #### Variant ####
  * itemid - Id of the Item
  * name - name of the variant
  * sellingPrice - the selling price
  * costPrice - the cost price
  * quantity - quantity of the items in stock
  * properties - A map of property name (String) to value (String).

### System Requirements ###
  * Java 8 must be installed and JAVA_HOME path is set properly
  * MySQL database

### Usage ###
  * Run the sql queries present in the file "database_schema.sql"  
  * Run the sql queries present in the file "sample_data.sql"  
  * Start the server by running the below command
  * java com.weavedin.inventory_mgmt.server.InventoryManagementHTTPServer   
  * The server will run on port 9090

### REST APIS ###
##### Add New Item #####
**Method:** POST  
**URL:** http://localhost:9090/item?userid=1&branchid=1  
**Data:**
{
"branchId" : 1,
"name": "Item 1",
"brand": "Brand 1",
"category" : "Category 1",
"productCode" : "Prod Code 1"
}  
**Response:** {"id":1,"branchId":1,"name":"Branch 1","brand":"Brand 1","category":"Category 1","productCode":"Prod Code 1"}      
  
##### Edit Item #####
**Method:** PUT  
**URL:** http://localhost:9090/item/1?userid=1&branchid=1  
**Data:**
{
"branchId" : 1,
"id": 1,
"name": "Item 3.1",
"brand": "Brand 1",
"category" : "Category 1",
"productCode" : "Prod Code 1"
}  
**Response:** {"id":1,"branchId":1,"name":"Item 3.1","brand":"Brand 1","category":"Category 1","productCode":"Prod Code 1"}    

##### Delete Item #####
**Method:** DELETE  
**URL:** http://localhost:9090/item/3?userid=1&branchid=1   
**Response:** true    

##### List Items #####
**Method:** GET  
**URL:** http://localhost:9090/item?userid=1&branchid=1  
**Response:** [{"id":1,"branchId":1,"name":"Item 1","brand":"Brand 1","category":"Category 1","productCode":"Prod Code 1"},{"id":2,"branchId":1,"name":"Item 2.2","brand":"Brand 2","category":"Category 2","productCode":"Prod Code 2"}]  

##### Add New Variant #####
**Method:** POST  
**URL:** http://localhost:9090/variant?userid=1&branchid=1&itemid=80  
**Data:**
{"itemId":80,"name":"Variant 3","sellingPrice":100.5,"costPrice":90.75,"quantity":10,"properties":{"cloth":"Cotton"}}  
**Response:** {"id":25,"itemId":80,"name":"Variant 3","sellingPrice":100.5,"costPrice":90.75,"quantity":10,"properties":{"cloth":"Cotton"}}      
  
##### Edit Variant #####
**Method:** PUT  
**URL:** http://localhost:9090/variant/25?userid=1&branchid=1&itemid=80  
**Data:**
{"id":25,"itemId":80,"name":"Variant 3.1","sellingPrice":100.5,"costPrice":90.75,"quantity":10,"properties":{"cloth":"Cotton", "Size":"L"}}  
**Response:** {"id":25,"itemId":80,"name":"Variant 3.1","sellingPrice":100.5,"costPrice":90.75,"quantity":10,"properties":{"Size":"L","cloth":"Cotton"}}    

##### Delete Variant #####
**Method:** DELETE  
**URL:** http://localhost:9090/variant/25?userid=1&branchid=1&itemid=80   
**Response:** true    

##### List Variants #####
**Method:** GET  
**URL:** http://localhost:9090/variant?userid=1&branchid=1&itemid=80  
**Response:** [{"id":20,"itemId":80,"name":"Variant 1.1","sellingPrice":100.5,"costPrice":90.75,"quantity":10,"properties":{"cloth":"Cotton"}},{"id":21,"itemId":80,"name":"Variant 1.2","sellingPrice":120.5,"costPrice":1000.75,"quantity":13,"properties":{}},{"id":23,"itemId":80,"name":"Variant 2.1","sellingPrice":120.5,"costPrice":1000.75,"quantity":13,"properties":{}}]  

