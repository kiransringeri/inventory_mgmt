package com.weavedin.inventory_mgmt.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.weavedin.inventory_mgmt.Item;
import com.weavedin.inventory_mgmt.Variant;
import com.weavedin.inventory_mgmt.api.APIResponse;
import com.weavedin.inventory_mgmt.dao.hibernate.UserActionHibernateDAO;
import com.weavedin.inventory_mgmt.impl.InventoryManagementImpl;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.IStatus;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import fi.iki.elonen.router.RouterNanoHTTPD;
import fi.iki.elonen.util.ServerRunner;

public class InventoryManagementHTTPServer extends RouterNanoHTTPD {

  private static final int PORT = 9090;

  /**
   * Create the server instance
   * 
   * @param port
   */
  public InventoryManagementHTTPServer() {
    super(PORT);
    addMappings();
    System.out.println("\nRunning! Point your browers to http://localhost:" + PORT + "/ \n");
  }

  /**
   * Add the routes Every route is an absolute path Parameters starts with ":" Handler class should
   * implement @UriResponder interface If the handler not implement UriResponder interface -
   * toString() is used
   */
  @Override
  public void addMappings() {
    super.addMappings();
    addRoute("/item", ItemHandler.class);
    addRoute("/item/:id", ItemHandler.class);

    addRoute("/variant", VariantHandler.class);
    addRoute("/variant/:id", VariantHandler.class);

    addRoute("/feeds", UserActionFeedHandler.class);
  }

  private static String getQueryParameter(Map<String, List<String>> queryParams, String param) {
    List<String> list = queryParams.get(param);
    if (list == null || list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

  public static class UserActionFeedHandler extends RequestHandler {
    protected APIResponse getData(MethodType method, Long id, Map<String, List<String>> queryParams,
        String content) throws Exception {
      APIResponse retObj = null;
      switch (method) {
        case GET:
          String fromStr = getQueryParameter(queryParams, "from");
          String tillStr = getQueryParameter(queryParams, "till");
          String userIdStr = getQueryParameter(queryParams, "userid");
          SimpleDateFormat df =
              new SimpleDateFormat(UserActionHibernateDAO.JAVA_DATE_FORMT_TILL_SECOND);
          Date from = fromStr == null ? null : df.parse(fromStr);
          Date till = tillStr == null ? null : df.parse(tillStr);
          Long userId =
              (userIdStr == null || userIdStr.isEmpty()) ? null : Long.parseLong(userIdStr);

          InventoryManagementImpl impl = new InventoryManagementImpl();
          return impl.getUserActions(from, till, userId);
      }
      return retObj;
    }
  }

  public static class ItemHandler extends RequestHandler {
    protected APIResponse getData(MethodType method, Long id, Map<String, List<String>> queryParams,
        String content) throws Exception {
      InventoryManagementImpl impl = new InventoryManagementImpl();
      long userId = Long.parseLong(queryParams.get("userid").get(0));
      long branchId = Long.parseLong(queryParams.get("branchid").get(0));
      switch (method) {
        case GET:
          if (id != null && id > 0) {
            return impl.getItem(userId, branchId, id);
          } else {
            return impl.getItems(userId, branchId);
          }
        case DELETE:
          return impl.deleteItem(userId, branchId, id);
        case POST:
          Item item = new Gson().fromJson(content, Item.class);
          return impl.saveItem(userId, branchId, item);
        case PUT:
          item = new Gson().fromJson(content, Item.class);
          return impl.updateItem(userId, branchId, item);
      }
      return null;
    }
  }

  public static class VariantHandler extends RequestHandler {
    protected APIResponse getData(MethodType method, Long id, Map<String, List<String>> queryParams,
        String content) throws Exception {
      APIResponse<Variant> retObj = null;
      InventoryManagementImpl impl = new InventoryManagementImpl();
      long userId = Long.parseLong(queryParams.get("userid").get(0));
      long branchId = Long.parseLong(queryParams.get("branchid").get(0));
      switch (method) {
        case GET:
          if (id != null && id > 0) {
            return impl.getVariant(userId, branchId, id);
          } else {
            long itemId = Long.parseLong(queryParams.get("itemid").get(0));
            return impl.getVariants(userId, branchId, itemId);
          }
        case DELETE:
          return impl.deleteVariant(userId, branchId, id);
        case POST:
          long itemId = Long.parseLong(queryParams.get("itemid").get(0));
          Variant variant = new Gson().fromJson(content, Variant.class);
          return impl.saveVariant(userId, branchId, itemId, variant);
        case PUT:
          itemId = Long.parseLong(queryParams.get("itemid").get(0));
          variant = new Gson().fromJson(content, Variant.class);
          return impl.updateVariant(userId, branchId, itemId, variant);
      }
      return null;
    }
  }

  private static final String MIME_HTML = "text/html";
  private static final String MIME_JSON = "application/json";

  private static enum MethodType {
    GET, POST, PUT, DELETE, OTHER
  }
  public static abstract class RequestHandler extends DefaultHandler {

    @Override
    public String getText() {
      return null;
    }

    @Override
    public IStatus getStatus() {
      return Status.OK;
    }

    @Override
    public String getMimeType() {
      return MIME_JSON;
    }

    @Override
    public Response get(UriResource uriResource, Map<String, String> urlParams,
        IHTTPSession session) {
      try {
        return get_response(MethodType.GET, uriResource, urlParams, session);
      } catch (Throwable th) {
        return getErrorResponse(th);
      }
    }

    private Response getErrorResponse(Throwable th) {
      if (th != null) {
        th.printStackTrace();
      }
      String errMsg = "Internal Server Error";
      if (th != null) {
        errMsg = th.toString();
      }
      return NanoHTTPD.newFixedLengthResponse(Status.INTERNAL_ERROR, getMimeType(), errMsg);
    }

    @Override
    public Response post(UriResource uriResource, Map<String, String> urlParams,
        IHTTPSession session) {
      try {
        return get_response(MethodType.POST, uriResource, urlParams, session);
      } catch (Throwable th) {
        return getErrorResponse(th);
      }
    }

    @Override
    public Response put(UriResource uriResource, Map<String, String> urlParams,
        IHTTPSession session) {
      try {
        return get_response(MethodType.PUT, uriResource, urlParams, session);
      } catch (Throwable th) {
        return getErrorResponse(th);
      }
    }

    @Override
    public Response delete(UriResource uriResource, Map<String, String> urlParams,
        IHTTPSession session) {
      try {
        return get_response(MethodType.DELETE, uriResource, urlParams, session);
      } catch (Throwable th) {
        return getErrorResponse(th);
      }
    }

    @Override
    public Response other(String method, UriResource uriResource, Map<String, String> urlParams,
        IHTTPSession session) {
      try {
        return get_response(MethodType.OTHER, uriResource, urlParams, session);
      } catch (Throwable th) {
        return getErrorResponse(th);
      }
    }

    private Response get_response(MethodType methodType, UriResource uriResource,
        Map<String, String> urlParams, IHTTPSession session) throws Throwable {
      long id = 0;
      if (urlParams.containsKey("id")) {
        id = Long.parseLong(urlParams.get("id"));
      }

      String content = getBody(session);

      APIResponse result = getData(methodType, id, session.getParameters(), content);
      if (result != null && result.isError()) {
        Throwable th = result.getException();
        if (th == null) {
          th = new Exception("Internal Server Error");
        }
        throw th;
      }
      Gson gson = new Gson();
      String json = result == null ? "" : gson.toJson(result.getReturnData());

      return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), json);
    }

    protected abstract APIResponse getData(MethodType method, Long id,
        Map<String, List<String>> queryParams, String content) throws Exception;

  }

  private static String getBody(IHTTPSession session) throws Exception {
    Map<String, String> files = new HashMap<String, String>();
    session.parseBody(files);
    if (files.isEmpty()) {
      return null;
    }

    if (files.containsKey("postData")) {
      return files.get("postData");
    } else if (files.containsKey("content")) {
      String fileName = files.get("content");
      StringBuilder buffer = new StringBuilder();
      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line = null;
        while ((line = br.readLine()) != null) {
          if (buffer.length() > 0) {
            buffer.append("\n");
          }
          buffer.append(line);
        }
      } catch (Exception ex) {
        System.err.println("Error reading file " + fileName);
        throw ex;
      }
      return buffer.toString();
    }

    return null;
  }

  /**
   * Main entry point
   * 
   * @param args
   */
  public static void main(String[] args) {
    ServerRunner.run(InventoryManagementHTTPServer.class);
  }
}
