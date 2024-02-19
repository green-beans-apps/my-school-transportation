package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidMonthlyPaymentExpirationException;
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
  public Student execute(UUID StudentId, String name, String school, String grade, String transportationType, Double monthlyPayment, Integer monthlyPaymentExpiration, String shift) {

    Optional<Student> student = studentRepository.findById(StudentId);

    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }

    student.get().setName(name);
    student.get().setSchool(school);
    student.get().setGrade(grade);
    student.get().setTransportationType(transportationType);
    student.get().setMonthlyPayment(monthlyPayment);
    student.get().setMonthlyPaymentExpiration(monthlyPaymentExpiration);
    student.get().setShift(shift);

    return studentRepository.updateStudent(student.get());
  }
}
