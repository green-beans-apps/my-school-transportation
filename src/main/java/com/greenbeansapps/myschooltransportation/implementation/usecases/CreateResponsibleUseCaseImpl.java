package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateResponsibleUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateResponsibleRequest;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateResponsibleUseCaseImpl implements CreateResponsibleUseCase {
    private final ResponsibleRepository responsibleRepo;

    public CreateResponsibleUseCaseImpl(ResponsibleRepository responsibleRepo) {
        this.responsibleRepo = responsibleRepo;
    }

    @Override
    public Responsible execute(CreateResponsibleRequest data) {
        Responsible newResponsible = new Responsible(data.id(), data.name(), data.email(), data.phone());
        this.responsibleRepo.create(newResponsible);
        return newResponsible;
    }
}
