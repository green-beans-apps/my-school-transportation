package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.PaymentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPaymentRepositoryJPA extends JpaRepository<Payment, UUID> {
    @Query("SELECT p FROM Payment p WHERE p.student.id = :studentId AND p.paymentMonth = :paymentMonth")
    Optional<Payment> findPaymentPerMonth(UUID studentId, Months paymentMonth);


    @Query("SELECT p " +
            "FROM Payment p WHERE p.student.id = :studentId")
    public List<Payment> findAllPaymentByStudentId(UUID studentId);

    @Query("SELECT p " +
            "FROM Payment p WHERE p.student.id = :studentId")
    public List<Payment>
    findAllPaymentsForSummary(UUID studentId);

}
