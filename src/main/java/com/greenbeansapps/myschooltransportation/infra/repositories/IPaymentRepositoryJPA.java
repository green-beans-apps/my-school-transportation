package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.PaymentSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepositoryJPA extends JpaRepository<PaymentSchema, UUID> {
    Optional<PaymentSchema> findPaymentPerMonth(UUID studentId, Months months);
}
