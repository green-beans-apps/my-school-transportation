package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class PasswordIsNotValidException extends RuntimeException {
    public PasswordIsNotValidException() {
        super("The password must contain at least 6 characters.");
    }
}
