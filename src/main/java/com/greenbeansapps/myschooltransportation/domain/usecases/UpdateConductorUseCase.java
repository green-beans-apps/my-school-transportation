package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateConductorRequest;

import java.util.UUID;

public interface UpdateConductorUseCase {
    public Conductor execute(UpdateConductorRequest data);
}
