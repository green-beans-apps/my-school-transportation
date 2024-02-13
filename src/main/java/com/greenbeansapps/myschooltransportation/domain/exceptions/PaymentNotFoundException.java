package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(){super("Payment not found.");}
}
