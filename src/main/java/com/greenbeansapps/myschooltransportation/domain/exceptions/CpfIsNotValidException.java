package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class CpfIsNotValidException extends RuntimeException {
  public CpfIsNotValidException() {
    super("CPF is not valid.");
  }
}
