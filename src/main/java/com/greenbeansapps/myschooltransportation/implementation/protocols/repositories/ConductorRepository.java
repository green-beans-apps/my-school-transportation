package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.dto.ConductorProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.ConductorProjection;

import java.util.Optional;
import java.util.UUID;

public interface ConductorRepository {
    public Conductor create(Conductor conductor);
    public Optional<Conductor> findByCpf(String cpf);
    public Optional<Conductor> findByEmail(String email);
    public Optional<Conductor> findById(UUID conductorId);
    public Optional<ConductorProjectionDto> findConductorByIdWithoutPassword(UUID conductorId);
    public Conductor updateConductor(Conductor conductor);
}
