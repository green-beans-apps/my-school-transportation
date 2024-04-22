package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateConductorRequest;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public interface CreateConductorUseCase {
    public Conductor execute(CreateConductorRequest data);
}
