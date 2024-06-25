package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;

import java.util.UUID;

public interface GetStudentByIdUseCase {
  public StudentWithPayments execute(UUID studentId);
}
