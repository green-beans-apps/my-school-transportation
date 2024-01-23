package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class InvalidEmailException extends RuntimeException {
  public InvalidEmailException() {
    super("Email is not valid.");
  }
}
