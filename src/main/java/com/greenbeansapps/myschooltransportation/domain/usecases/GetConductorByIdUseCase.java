package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.GetConductorByIdResponse;

import java.util.UUID;

public interface GetConductorByIdUseCase {
    public GetConductorByIdResponse execute(UUID conductorId);
}
