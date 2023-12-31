package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exeptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.GetStudentByIdUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;

import java.util.Optional;
import java.util.UUID;

public class GetStudentByIdUseCaseImpl implements GetStudentByIdUseCase {

  private final StudentRepository studentRepo;

  public GetStudentByIdUseCaseImpl(StudentRepository studentRepo) {
    this.studentRepo = studentRepo;
  }

  @Override
  public Student execute(UUID studentId) {
    Optional<Student> student = this.studentRepo.findOneById(studentId);
    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }
    return student.get();
  }
}
