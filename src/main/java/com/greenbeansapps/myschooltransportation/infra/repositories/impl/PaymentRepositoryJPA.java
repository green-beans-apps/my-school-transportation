package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IPaymentRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.PaymentProjection;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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
        this.paymentRepo.save(payment);
        return payment;
    }

    @Override
    public Optional<Payment> findPayment(UUID paymentId) {
        Optional<Payment> payment = this.paymentRepo.findById(paymentId);
        if (payment.isEmpty()) {
            return Optional.empty();
        }
        return payment;
    }

    @Override
    public Optional<Payment> findPaymentPerMonth(UUID studentId, Months months) {
        Optional<Payment> payment = this.paymentRepo.findPaymentPerMonth(studentId, months);
        if (payment.isEmpty()) {
            return Optional.empty();
        }

        return payment;
    }

    @Override
    public List<Payment> findAllPaymentByStudentId(UUID studentId) {
        List<PaymentProjection> paymentSchemas = this.paymentRepo.findAllPaymentByStudentId(studentId);
        if (paymentSchemas.isEmpty()) {
            return null;
        }

        List<Payment> paymentProjectionDtoList = new ArrayList<>();
        for (PaymentProjection paymentSchema : paymentSchemas) {
            paymentProjectionDtoList.add(new Payment(paymentSchema.getId(), paymentSchema.getPaymentDate(), paymentSchema.getPaymentMonth(), null));
        }

        return paymentProjectionDtoList;
    }

    @Override
    public Boolean cancelPayment(UUID paymentId) {
        Optional<Payment> payment = this.paymentRepo.findById(paymentId);
        if (payment.isEmpty()) {
            return false;
        }

        paymentRepo.delete(payment.get());
        return true;
    }
}
