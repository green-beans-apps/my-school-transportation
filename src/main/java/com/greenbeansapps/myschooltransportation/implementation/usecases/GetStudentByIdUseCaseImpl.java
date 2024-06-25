package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.GetStudentByIdUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetStudentByIdUseCaseImpl implements GetStudentByIdUseCase {

  private final StudentRepository studentRepo;
  private final PaymentRepository paymentRepo;

  public GetStudentByIdUseCaseImpl(StudentRepository studentRepo, PaymentRepository paymentRepo) {
    this.studentRepo = studentRepo;
    this.paymentRepo = paymentRepo;
  }

  @Override
  public StudentWithPayments execute(UUID studentId) {
    Optional<StudentWithPayments> student = this.studentRepo.getStudentWithPayments(studentId);
    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }

   return student.get();
  }
}
