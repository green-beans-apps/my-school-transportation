package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;

import java.util.UUID;

public interface CreateResponsibleUseCase {
    public Responsible execute(UUID id, String name, String email, String phone);
}
