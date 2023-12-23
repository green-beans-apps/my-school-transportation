package com.greenbeansapps.myschooltransportation.domain.exeptions;

public class EmailAlreadyRegisteredException extends RuntimeException{
    public EmailAlreadyRegisteredException() {
        super("Email Already Registered.");
    }
}
