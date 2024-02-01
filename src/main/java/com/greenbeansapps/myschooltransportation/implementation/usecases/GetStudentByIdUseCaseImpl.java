package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.GetStudentByIdUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetStudentByIdUseCaseImpl implements GetStudentByIdUseCase {

  private final StudentRepository studentRepo;

  public GetStudentByIdUseCaseImpl(StudentRepository studentRepo) {
    this.studentRepo = studentRepo;
  }

  @Override
  public StudentProjectionDto execute(UUID studentId) {
    Optional<StudentProjectionDto> student = this.studentRepo.findStudentByIdWithoutConductor(studentId);
    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }
    return student.get();
  }
}
