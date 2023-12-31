package com.greenbeansapps.myschooltransportation.domain.exeptions;

public class StudentNotFoundException extends RuntimeException {
  public StudentNotFoundException() {
    super("Student not found.");
  }
}
