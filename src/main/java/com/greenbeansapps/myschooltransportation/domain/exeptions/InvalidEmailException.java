package com.greenbeansapps.myschooltransportation.domain.exeptions;

public class InvalidEmailException extends RuntimeException {
  public InvalidEmailException() {
    super("Email is not valid.");
  }
}
