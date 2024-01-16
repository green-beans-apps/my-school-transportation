package com.greenbeansapps.myschooltransportation.main.controllers.erros;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
  private Map<String, String> errors;

  public ErrorResponse() {
    this.errors = new HashMap<>();
  }

  public ErrorResponse(String field, String message) {
    this();
    addError(field, message);
  }

  public void addError(String field, String message) {
    errors.put(field, message);
  }

  public Map<String, String> getErrors() {
    return errors;
  }
}
