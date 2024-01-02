package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
  public Payment register(Payment payment);
  public Optional<Payment> findPaymentPerMonth(UUID studentId, Months months);
}
