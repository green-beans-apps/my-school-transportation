package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException{
    public EmailAlreadyRegisteredException() {
        super("Email Already Registered.");
    }
}
