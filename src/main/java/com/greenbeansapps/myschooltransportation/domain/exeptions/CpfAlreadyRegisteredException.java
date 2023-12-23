package com.greenbeansapps.myschooltransportation.domain.exeptions;

public class CpfAlreadyRegisteredException extends RuntimeException {
    public CpfAlreadyRegisteredException() {
        super("CPF Already Registered.");
    }
}
