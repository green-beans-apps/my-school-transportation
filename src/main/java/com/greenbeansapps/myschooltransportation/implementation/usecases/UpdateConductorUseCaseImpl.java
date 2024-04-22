package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ConductorNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.UpdateConductorUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateConductorRequest;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateConductorUseCaseImpl implements UpdateConductorUseCase {
    private final ConductorRepository conductorRepo;

    public UpdateConductorUseCaseImpl(ConductorRepository conductorRepo) {
        this.conductorRepo = conductorRepo;
    }

    @Override
    public Conductor execute(UpdateConductorRequest data) {
        Optional<Conductor> conductor = conductorRepo.findById(data.conductorId());

        if (conductor.isEmpty()) {
            throw new ConductorNotFoundException();
        }

        conductor.get().setName(data.name());
        conductor.get().setEmail(data.email());

        return conductorRepo.updateConductor(conductor.get());
    }
}
