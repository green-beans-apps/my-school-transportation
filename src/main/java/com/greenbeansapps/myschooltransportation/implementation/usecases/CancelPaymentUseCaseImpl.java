package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.exceptions.PaymentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.CancelPaymentUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CancelPaymentUseCaseImpl implements CancelPaymentUseCase {

    private final PaymentRepository paymentRepo;

    public CancelPaymentUseCaseImpl(PaymentRepository paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    @Override
    public Boolean execute(UUID paymentId) {
        Optional<Payment> getPayment = this.paymentRepo.findPayment(paymentId);
        if (getPayment.isEmpty()) {
            throw new PaymentNotFoundException();
        }

        this.paymentRepo.cancelPayment(paymentId);
        return true;
    }
}
