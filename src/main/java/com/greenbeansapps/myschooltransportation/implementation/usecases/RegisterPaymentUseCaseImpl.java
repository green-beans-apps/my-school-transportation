package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ExistingPaymentException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidMonthException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.RegisterPaymentUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.RegisterPaymentRequest;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IMonthlyFeeRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterPaymentUseCaseImpl implements RegisterPaymentUseCase {
  private final PaymentRepository paymentRepo;
  private final StudentRepository studentRepo;
  @Autowired
  private IMonthlyFeeRepositoryJPA monthlyFeeRepo;

  public RegisterPaymentUseCaseImpl(PaymentRepository paymentRepo, StudentRepository studentRepo) {
    this.paymentRepo = paymentRepo;
    this.studentRepo = studentRepo;
  }

  @Override
  public Payment execute(RegisterPaymentRequest data) {
    Optional<Student> student = this.studentRepo.findById(data.studentId());
    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }

      //Verifica se o valor inserido é válido
      Months month = validateMonth(data.paymentMonth());

    MonthlyFee monthlyFee = monthlyFeeRepo.findById(data.monthlyFeeId()).orElseThrow(RuntimeException::new);

    Payment newPayment = new Payment();
    newPayment.setPaymentDate(new Date());
    newPayment.setPaymentMonth(month);
    newPayment.setPaymentYear(data.paymentYear());
    newPayment.setAmount(data.paymentAmount());
    newPayment.setStudent(student.get());
    newPayment.setMonthlyFee(monthlyFee);
    return this.paymentRepo.register(newPayment);
  }

  private Months validateMonth(String paymentMonth) {
    try {
      return Months.valueOf(paymentMonth.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new InvalidMonthException();
    }
  }
}
