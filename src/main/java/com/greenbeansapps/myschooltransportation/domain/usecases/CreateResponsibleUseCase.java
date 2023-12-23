package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;

public interface CreateResponsibleUseCase {
    public Responsible execute(String name, String email, String phoneNumber);
}
