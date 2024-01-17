package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exeptions.ExistingPaymentException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.InvalidMonthException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.usecases.RegisterPaymentUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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

        try {
            Months monthEnum = Months.valueOf(registerPaymentDto.month.toUpperCase());
            this.registerPaymentUseCase.execute(registerPaymentDto.studentId, monthEnum);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (StudentNotFoundException studentNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(studentNotFoundException.getMessage());
        } catch (InvalidMonthException invalidMonthException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidMonthException);
        } catch (ExistingPaymentException existingPaymentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(existingPaymentException);
        } catch (Error err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }

    public record RegisterPaymentDto(@NotNull UUID studentId, @NotBlank String month) {}
}