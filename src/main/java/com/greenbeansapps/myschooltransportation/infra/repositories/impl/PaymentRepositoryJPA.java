package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IPaymentRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.PaymentSchema;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryJPA implements PaymentRepository {

    private final IPaymentRepositoryJPA paymentRepo;

    public PaymentRepositoryJPA(IPaymentRepositoryJPA paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public Payment register(Payment payment) {
        var newPayment = new PaymentSchema();
        BeanUtils.copyProperties(payment, newPayment);
        this.paymentRepo.save(newPayment);
        return payment;
    }

    @Override
    public Optional<Payment> findPaymentPerMonth(UUID studentId, Months months) {
        Optional<PaymentSchema> paymentSchema = this.paymentRepo.findPaymentPerMonth(studentId, months);
        if (paymentSchema.isEmpty()) {
            return Optional.empty();
        }
        Student newStudent = new Student();
        BeanUtils.copyProperties(paymentSchema.get().getStudent(), newStudent);

        return Optional.of(new Payment(paymentSchema.get().getId(), paymentSchema.get().getPaymentDate(), paymentSchema.get().getPaymentMonth(), newStudent));
    }
}
