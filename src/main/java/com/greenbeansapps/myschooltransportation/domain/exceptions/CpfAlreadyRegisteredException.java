package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class CpfAlreadyRegisteredException extends RuntimeException {
    public CpfAlreadyRegisteredException() {
        super("CPF Already Registered.");
    }
}
