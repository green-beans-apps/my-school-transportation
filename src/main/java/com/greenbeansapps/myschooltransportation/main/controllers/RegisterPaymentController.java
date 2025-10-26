package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.RegisterPaymentRequest;
import com.greenbeansapps.myschooltransportation.implementation.usecases.RegisterPaymentUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class RegisterPaymentController {

    @Autowired
    private RegisterPaymentUseCaseImpl registerPaymentUseCase;

    @PostMapping()
    public ResponseEntity registerPayment(@RequestBody @Valid RegisterPaymentDto registerPaymentDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        this.registerPaymentUseCase.execute(new RegisterPaymentRequest(registerPaymentDto.studentId, registerPaymentDto.paymentMonth, registerPaymentDto.paymentYear, registerPaymentDto.paymentAmount, registerPaymentDto.monthlyFeeId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public record RegisterPaymentDto(@NotNull UUID studentId, @NotBlank String paymentMonth, @NotNull Integer paymentYear, @NotNull
                                     BigDecimal paymentAmount, @NotNull UUID monthlyFeeId) {}
}
