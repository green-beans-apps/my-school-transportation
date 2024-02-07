package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.ConductorProjectionDto;

import java.util.UUID;

public interface GetConductorByIdUseCase {
    public ConductorProjectionDto execute(UUID conductorId);
}
