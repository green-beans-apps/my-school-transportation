package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public interface CreateConductorUseCase {
    public Conductor execute(UUID id, String name, String email, String password, String cpf);
}
