package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;

import java.util.UUID;

public interface UpdateConductorUseCase {
    public Conductor execute(UUID conductorId, String name, String email);
}
