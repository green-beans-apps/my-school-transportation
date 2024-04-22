package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.PaymentSchema;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
  public Payment register(Payment payment);
  public Optional<Payment> findPayment(UUID paymentId);
  public Optional<Payment> findPaymentPerMonth(UUID studentId, Months months);
  public List<Payment> findAllPaymentByStudentId(UUID studentId);
  public Boolean cancelPayment(UUID paymentId);
}
