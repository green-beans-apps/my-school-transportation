package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.UpdateStudentUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateStudentUseCaseImpl implements UpdateStudentUseCase {

  private final StudentRepository studentRepository;

  public UpdateStudentUseCaseImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }
  @Override
  public Student execute(UUID StudentId, String name, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration) {

    Optional<Student> student = studentRepository.findById(StudentId);

    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }

    student.get().setName(name);
    student.get().setSchool(school);
    student.get().setGrade(grade);
    student.get().setMonthlyPayment(monthlyPayment);
    student.get().setMonthlyPaymentExpiration(monthlyPaymentExpiration);

    return studentRepository.updateStudent(student.get());
  }
}
