package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.PaymentSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepositoryJPA extends JpaRepository<PaymentSchema, UUID> {
    @Query("SELECT p FROM PaymentSchema p WHERE p.student.id = :studentId AND p.paymentMonth = :paymentMonth")
    Optional<PaymentSchema> findPaymentPerMonth(UUID studentId, Months paymentMonth);
}
