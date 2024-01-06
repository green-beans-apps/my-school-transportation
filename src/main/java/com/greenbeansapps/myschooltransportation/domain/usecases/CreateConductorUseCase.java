package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;

import java.security.NoSuchAlgorithmException;

public interface CreateConductorUseCase {
    public Conductor execute(String name, String email, String password, String cpf) throws NoSuchAlgorithmException;
}
