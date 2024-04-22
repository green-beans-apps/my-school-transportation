package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateConductorRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateResponsibleRequest;

import java.util.UUID;

public interface CreateResponsibleUseCase {
    public Responsible execute(CreateResponsibleRequest data);
}
