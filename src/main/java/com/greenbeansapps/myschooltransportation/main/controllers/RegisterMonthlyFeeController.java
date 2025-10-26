package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidMonthException;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.RegisterMonthlyFeeRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.RegisterPaymentRequest;
import com.greenbeansapps.myschooltransportation.implementation.usecases.RegisterMonthlyFeeImpl;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/MonthlyFee")
public class RegisterMonthlyFeeController {

    @Autowired
    private RegisterMonthlyFeeImpl registerMonthlyFeeUseCase;

    @PostMapping()
    public ResponseEntity registerMonthlyFee(@RequestBody @Valid RegisterMonthlyFeeDto registerMonthlyFeeDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Months month = validateMonth(registerMonthlyFeeDto.referenceMonth);

        this.registerMonthlyFeeUseCase.execute(new RegisterMonthlyFeeRequest(registerMonthlyFeeDto.studentId, month,registerMonthlyFeeDto.referenceYear, registerMonthlyFeeDto.amount));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public record RegisterMonthlyFeeDto(@NotNull UUID studentId, @NotBlank String referenceMonth, @NotBlank String referenceYear, @NotNull BigDecimal amount) {}

    private Months validateMonth(String paymentMonth) {
        try {
            return Months.valueOf(paymentMonth.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidMonthException();
        }
    }
}
