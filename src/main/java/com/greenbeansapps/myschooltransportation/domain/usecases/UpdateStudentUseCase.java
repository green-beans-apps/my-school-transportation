package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateStudentRequest;

import java.util.UUID;

public interface UpdateStudentUseCase {
  Student execute(UpdateStudentRequest data);
}
