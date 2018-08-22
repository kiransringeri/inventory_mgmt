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
  * Maven

### Usage ###
  * Run the sql queries present in the file "database_schema.sql"  
  * Run the sql queries present in the file "sample_data.sql" 
  * Run "mvn clean verify". This will generate the jar file.
  * Run "java -jar target/inventory_mgmt-0.1.jar"   
  * The server will run on port 9090
  * Then use the REST client and refer to below REST APIs

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
**URL:** [http://localhost:9090/variant?userid=1&branchid=1&itemid=80](http://localhost:9090/variant?userid=1&branchid=1&itemid=80)  
**Response:** [{"id":20,"itemId":80,"name":"Variant 1.1","sellingPrice":100.5,"costPrice":90.75,"quantity":10,"properties":{"cloth":"Cotton"}},{"id":21,"itemId":80,"name":"Variant 1.2","sellingPrice":120.5,"costPrice":1000.75,"quantity":13,"properties":{}},{"id":23,"itemId":80,"name":"Variant 2.1","sellingPrice":120.5,"costPrice":1000.75,"quantity":13,"properties":{}}]  

##### Get User Action Feeds #####
**Method:** GET  
**URL:** [http://localhost:9090/feeds?userid=1&from=07-07-2018 09:00:00&till=07-10-2018 09:00:00](http://localhost:9090/feeds?userid=1&from=07-07-2018 09:00:00&till=07-10-2018 09:00:00)  
**Response:** [{"time":"Aug 21, 2018 12:47:33 PM","userId":1,"userName":"John","actions":"Deleted variant Variant 3.1 of item Item 2.2"},{"time":"Aug 21, 2018 12:46:54 PM","userId":1,"userName":"John","actions":"Edited name,Size of variant Variant 3.1 of item Item 2.2"},{"time":"Aug 21, 2018 12:45:40 PM","userId":1,"userName":"John","actions":"Added variant Variant 3 of item Item 2.2"},{"time":"Aug 21, 2018 12:40:24 PM","userId":1,"userName":"John","actions":"Deleted item Item 3.1"},{"time":"Aug 21, 2018 12:34:22 PM","userId":1,"userName":"John","actions":"Edited name of item Item 3.1"},{"time":"Aug 21, 2018 12:31:00 PM","userId":1,"userName":"John","actions":"Added item Branch 1"},{"time":"Aug 21, 2018 12:14:27 PM","userId":1,"userName":"John","actions":"Deleted variant Variant 2.2 of item Item 2.2"},{"time":"Aug 21, 2018 12:09:51 PM","userId":1,"userName":"John","actions":"Added variant Variant 2.2 of item Item 2.2"},{"time":"Aug 21, 2018 12:09:45 PM","userId":1,"userName":"John","actions":"Added variant Variant 2.1 of item Item 2.2"},{"time":"Aug 21, 2018 12:08:26 PM","userId":1,"userName":"John","actions":"Edited size,cloth of variant Variant 1.1 of item Item 2.2"},{"time":"Aug 21, 2018 11:58:42 AM","userId":1,"userName":"John","actions":"Edited size of variant Variant 1.1 of item Item 2.2"},{"time":"Aug 21, 2018 11:55:41 AM","userId":1,"userName":"John","actions":"Added variant Variant 1.2 of item Item 2.2"},{"time":"Aug 21, 2018 11:55:22 AM","userId":1,"userName":"John","actions":"Added variant Variant 1.1 of item Item 2.2"},{"time":"Aug 21, 2018 11:49:45 AM","userId":1,"userName":"John","actions":"Deleted item Item 2.1"},{"time":"Aug 21, 2018 11:45:09 AM","userId":1,"userName":"John","actions":"Edited name of item Item 2.1"},{"time":"Aug 21, 2018 11:44:53 AM","userId":1,"userName":"John","actions":"Edited name of item Item 2.2"},{"time":"Aug 21, 2018 11:38:39 AM","userId":1,"userName":"John","actions":"Added item Item 2.1"},{"time":"Aug 21, 2018 11:38:30 AM","userId":1,"userName":"John","actions":"Added item Item 2"},{"time":"Aug 21, 2018 11:37:09 AM","userId":1,"userName":"John","actions":"Added item Item 1"}] 


It returns all feeds. If we want feeds only for a store or branch, then we can extend it to accept & support these parameters.
