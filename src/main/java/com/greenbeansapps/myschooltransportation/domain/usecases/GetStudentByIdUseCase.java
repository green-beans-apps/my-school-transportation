package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;

import java.util.UUID;

public interface GetStudentByIdUseCase {
  public Student execute(UUID studentId);
}
