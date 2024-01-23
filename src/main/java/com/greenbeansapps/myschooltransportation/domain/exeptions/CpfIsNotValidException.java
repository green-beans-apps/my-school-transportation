package com.greenbeansapps.myschooltransportation.domain.exeptions;

public class CpfIsNotValidException extends RuntimeException {
  public CpfIsNotValidException() {
    super("CPF is not valid.");
  }
}
