package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.ConductorProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ConductorNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.GetConductorByIdUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetConductorByIdUseCaseImpl implements GetConductorByIdUseCase {
    private final ConductorRepository conductorRepo;

    public GetConductorByIdUseCaseImpl(ConductorRepository conductorRepo) {
        this.conductorRepo = conductorRepo;
    }

    @Override
    public ConductorProjectionDto execute(UUID conductorId) {
        Optional<ConductorProjectionDto> conductor = this.conductorRepo.findConductorByIdWithoutPassword(conductorId);
        if (conductor.isEmpty()) {
            throw new ConductorNotFoundException();
        }

        return new ConductorProjectionDto(conductor.get().getId(), conductor.get().getName(), conductor.get().getEmail(), conductor.get().getCpf());
    }
}
