package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.implementation.usecases.CancelPaymentUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class CancelPaymentController {

    @Autowired
    private CancelPaymentUseCaseImpl cancelPaymentUseCase;

    @DeleteMapping("/{paymentId}")
    public ResponseEntity cancelPayment(@PathVariable("paymentId")UUID paymentId) {
        cancelPaymentUseCase.execute(paymentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
