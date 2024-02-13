package com.greenbeansapps.myschooltransportation.main.controllers;


import com.greenbeansapps.myschooltransportation.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CpfAlreadyRegisteredException.class)
    private ResponseEntity<String> cpfAlreadyRegisteredExceptionHandler(CpfAlreadyRegisteredException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    private ResponseEntity<String> emailAlreadyRegisteredExceptionHandler(EmailAlreadyRegisteredException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ExistingPaymentException.class)
    private ResponseEntity<String> existingPaymentExceptionHandler(ExistingPaymentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidMonthException.class)
    private ResponseEntity<String> invalidMonthExceptionHandler(InvalidMonthException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidAddressException.class)
    private ResponseEntity<String> invalidAddressExceptionHandler(InvalidAddressException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidConductorException.class)
    private ResponseEntity<String> invalidConductorExceptionHandler(InvalidConductorException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidResponsibleException.class)
    private ResponseEntity<String> invalidResponsibleExceptionHandler(InvalidResponsibleException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(PasswordIsNotValidException.class)
    private ResponseEntity<String> passwordIsNotValidExceptionHandler(PasswordIsNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    private ResponseEntity<String> studentNotFoundExceptionHandler(StudentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<String> runtimeExceptionHandler(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<String> nullPointerExceptionHandler(NullPointerException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidTransportationTypeException.class)
    private ResponseEntity<String> invalidTransportationTypeExceptionHandler(InvalidTransportationTypeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidShiftException.class)
    private ResponseEntity<String> invalidShiftExceptionHandler(InvalidShiftException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ConductorNotFoundException.class)
    private ResponseEntity<String> conductorNotFoundExceptionHandler(ConductorNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidMonthlyPaymentExpirationException.class)
    private ResponseEntity<String> invalidMonthlyPaymentExpirationException(InvalidMonthlyPaymentExpirationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    private ResponseEntity<String> paymentNotFoundException(PaymentNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
