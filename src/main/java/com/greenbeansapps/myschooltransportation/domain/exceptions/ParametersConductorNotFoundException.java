package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class ParametersConductorNotFoundException extends RuntimeException {
    public ParametersConductorNotFoundException() {
        super("Parameters Conductor not found");
    }
}
