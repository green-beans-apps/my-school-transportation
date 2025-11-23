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
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.MonthlyFeeRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RegisterPaymentUseCaseImpl implements RegisterPaymentUseCase {
  private final PaymentRepository paymentRepo;
  private final StudentRepository studentRepo;
  private MonthlyFeeRepository monthlyFeeRepo;

  public RegisterPaymentUseCaseImpl(PaymentRepository paymentRepo, StudentRepository studentRepo, MonthlyFeeRepository monthlyFeeRepo) {
    this.paymentRepo = paymentRepo;
    this.studentRepo = studentRepo;
    this.monthlyFeeRepo = monthlyFeeRepo;
  }

  @Override
  public Payment execute(RegisterPaymentRequest data) {
    Optional<Student> student = this.studentRepo.findById(data.studentId());
    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }

    Optional<Payment> payment = this.paymentRepo.findPayment(data.paymentId());
    if (payment.isPresent()) {
      throw new ExistingPaymentException();
    }

    //Verifica se o valor inserido é válido
    Months month = validateMonth(data.paymentMonth());

    MonthlyFee monthlyFee = monthlyFeeRepo.findMonthlyFeeByReferenceAndStudent(month, data.paymentYear(), data.studentId());

    Payment newPayment = new Payment();
    newPayment.setId(data.paymentId());
    newPayment.setPaymentDate(new Date());
    newPayment.setPaymentMonth(monthlyFee.getReferenceMonth());
    newPayment.setPaymentYear(Integer.parseInt(monthlyFee.getReferenceYear()));
    newPayment.setAmount(monthlyFee.getAmount());
    newPayment.setStudent(monthlyFee.getStudent());
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
