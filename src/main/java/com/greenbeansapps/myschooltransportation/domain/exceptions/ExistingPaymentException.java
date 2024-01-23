package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class ExistingPaymentException extends RuntimeException {
  public ExistingPaymentException() {
    super("Existing payment.");
  }
}
