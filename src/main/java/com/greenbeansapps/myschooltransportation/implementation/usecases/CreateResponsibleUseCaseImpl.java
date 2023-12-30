package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateResponsibleUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;

import java.util.UUID;

public class CreateResponsibleUseCaseImpl implements CreateResponsibleUseCase {
    private final ResponsibleRepository responsibleRepo;

    public CreateResponsibleUseCaseImpl(ResponsibleRepository responsibleRepo) {
        this.responsibleRepo = responsibleRepo;
    }

    @Override
    public Responsible execute(String name, String email, String phoneNumber) {
        Responsible newResponsible = new Responsible(UUID.randomUUID(), name, email, phoneNumber);
        this.responsibleRepo.create(newResponsible);
        return newResponsible;
    }
}
