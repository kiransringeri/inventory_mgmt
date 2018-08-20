package com.weavedin.inventory_mgmt.api;

/**
 * Class which is used as return type from API methods.
 * It will have fields to say whether the API call was a success or failure. 
 * It will contain the actual return object.
 * 
 * @author kiransringeri
 *
 * @param <T>
 */
public class APIResponse <T>{
  private boolean error = false;
  private Throwable exception = null;
  private T returnData;
  
  public boolean isError() {
    return error;
  }
  public void setError(boolean error) {
    this.error = error;
  }
  public Throwable getException() {
    return exception;
  }
  public void setException(Throwable exception) {
    this.exception = exception;
  }
  public T getReturnData() {
    return returnData;
  }
  public void setReturnData(T returnData) {
    this.returnData = returnData;
  }
}
