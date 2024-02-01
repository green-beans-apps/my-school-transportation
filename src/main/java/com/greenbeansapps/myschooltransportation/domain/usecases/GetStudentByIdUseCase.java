package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection;

import java.util.UUID;

public interface GetStudentByIdUseCase {
  public StudentProjectionDto execute(UUID studentId);
}
