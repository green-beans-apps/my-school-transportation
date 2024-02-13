package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.dto.PaymentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IPaymentRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.PaymentProjection;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.PaymentSchema;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.StudentSchema;
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
        var newPayment = new PaymentSchema();
        BeanUtils.copyProperties(payment, newPayment);

        var newStudent = new StudentSchema();
        BeanUtils.copyProperties(payment.getStudent(), newStudent);
        newPayment.setStudent(newStudent);

        this.paymentRepo.save(newPayment);
        return payment;
    }

    @Override
    public Optional<Payment> findPayment(UUID paymentId) {
        Optional<PaymentSchema> paymentSchema = this.paymentRepo.findById(paymentId);
        if (paymentSchema.isEmpty()) {
            return Optional.empty();
        }
        var newStudent = new Student();
        BeanUtils.copyProperties(paymentSchema.get().getStudent(), newStudent);

        return Optional.of(new Payment(paymentSchema.get().getId(), paymentSchema.get().getPaymentDate(), paymentSchema.get().getPaymentMonth(), newStudent));
    }

    @Override
    public Optional<Payment> findPaymentPerMonth(UUID studentId, Months months) {
        Optional<PaymentSchema> paymentSchema = this.paymentRepo.findPaymentPerMonth(studentId, months);
        if (paymentSchema.isEmpty()) {
            return Optional.empty();
        }
        var newStudent = new Student();
        BeanUtils.copyProperties(paymentSchema.get().getStudent(), newStudent);

        return Optional.of(new Payment(paymentSchema.get().getId(), paymentSchema.get().getPaymentDate(), paymentSchema.get().getPaymentMonth(), newStudent));
    }

    @Override
    public List<PaymentProjectionDto> findAllPaymentByStudentId(UUID studentId) {
        List<PaymentProjection> paymentSchemas = this.paymentRepo.findAllPaymentByStudentId(studentId);
        if (paymentSchemas.isEmpty()) {
            return null;
        }

        List<PaymentProjectionDto> paymentProjectionDtoList = new ArrayList<>();
        for (PaymentProjection paymentSchema : paymentSchemas) {
            paymentProjectionDtoList.add(new PaymentProjectionDto(paymentSchema.getId(), paymentSchema.getPaymentDate(), paymentSchema.getPaymentMonth()));
        }

        return paymentProjectionDtoList;
    }

    @Override
    public Boolean cancelPayment(UUID paymentId) {
        Optional<PaymentSchema> paymentSchema = this.paymentRepo.findById(paymentId);
        if (paymentSchema.isEmpty()) {
            return false;
        }

        paymentRepo.delete(paymentSchema.get());
        return true;
    }
}
