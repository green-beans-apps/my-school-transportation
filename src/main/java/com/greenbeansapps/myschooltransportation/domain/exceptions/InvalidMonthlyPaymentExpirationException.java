package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class InvalidMonthlyPaymentExpirationException extends RuntimeException{
    public InvalidMonthlyPaymentExpirationException(){super("The monthly payment expiration due date must be between 1 and 28.");}
}
