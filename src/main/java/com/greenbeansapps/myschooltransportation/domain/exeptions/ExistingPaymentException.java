package com.greenbeansapps.myschooltransportation.domain.exeptions;

public class ExistingPaymentException extends RuntimeException {
  public ExistingPaymentException() {
    super("Existing payment.");
  }
}
