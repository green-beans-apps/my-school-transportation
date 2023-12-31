package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exeptions.ExistingPaymentException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.RegisterPaymentUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class RegisterPaymentUseCaseImpl implements RegisterPaymentUseCase {
  private final PaymentRepository paymentRepo;
  private final StudentRepository studentRepo;

  public RegisterPaymentUseCaseImpl(PaymentRepository paymentRepo, StudentRepository studentRepo) {
    this.paymentRepo = paymentRepo;
    this.studentRepo = studentRepo;
  }

  @Override
  public Payment execute(UUID studentId, Months paymentMonth) {
    Optional<Student> student = this.studentRepo.findById(studentId);
    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }

    Optional<Payment> paymentExists = this.paymentRepo.findPaymentPerMonth(studentId, paymentMonth);
    if(paymentExists.isPresent()) {
      throw new ExistingPaymentException();
    }

    Payment newPayment = new Payment(UUID.randomUUID(), new Date(), paymentMonth, student.get());
    return this.paymentRepo.register(newPayment);
  }
}
