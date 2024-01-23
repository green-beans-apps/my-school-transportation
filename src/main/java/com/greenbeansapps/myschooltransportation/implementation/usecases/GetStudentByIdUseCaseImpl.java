package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.GetStudentByIdUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
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
  public Student execute(UUID studentId) {
    Optional<Student> student = this.studentRepo.findById(studentId);
    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }
    return student.get();
  }
}
