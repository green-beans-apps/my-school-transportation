package com.greenbeansapps.myschooltransportation.domain.exceptions;

public class ConductorNotFoundException extends RuntimeException{
    public ConductorNotFoundException() {super("Conductor not found.");}
}
