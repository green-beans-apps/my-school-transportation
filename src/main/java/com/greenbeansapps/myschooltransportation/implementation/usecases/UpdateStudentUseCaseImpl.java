package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidMonthlyPaymentExpirationException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.UpdateStudentUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateStudentRequest;
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
  public Student execute(UpdateStudentRequest data) {

    Optional<Student> student = studentRepository.findById(data.StudentId());

    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }

    student.get().setName(data.name());
    student.get().setSchool(data.school());
    student.get().setGrade(data.grade());
    student.get().setTransportationType(data.transportationType());
    student.get().setMonthlyPayment(data.monthlyPayment());
    student.get().setMonthlyPaymentExpiration(data.monthlyPaymentExpiration());
    student.get().setShift(data.shift());

    return studentRepository.updateStudent(student.get());
  }
}
