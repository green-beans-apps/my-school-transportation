package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class StudentNotFoundException extends RuntimeException {
  public StudentNotFoundException() {
    super("Student not found.");
  }
}
