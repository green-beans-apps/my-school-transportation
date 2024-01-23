package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;

import java.util.UUID;

public interface UpdateStudentUseCase {
  Student execute(UUID StudentId, String name, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration);
}
