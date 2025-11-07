package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class KernelNotfoundException extends RuntimeException {
    public KernelNotfoundException() {
        super("Kernel File not found.");
    }
}
