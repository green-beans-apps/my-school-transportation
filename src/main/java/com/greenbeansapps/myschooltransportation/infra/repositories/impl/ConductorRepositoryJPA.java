package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.dto.ConductorProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IConductorRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.ConductorProjection;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ConductorSchema;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ConductorRepositoryJPA implements ConductorRepository {

    private final IConductorRepositoryJPA conductorRepo;

    public ConductorRepositoryJPA(IConductorRepositoryJPA conductorRepo) {
        this.conductorRepo = conductorRepo;
    }

    @Override
    public Conductor create(Conductor conductor) {
        var newConductor = new ConductorSchema();
        BeanUtils.copyProperties(conductor, newConductor);
        this.conductorRepo.save(newConductor);
        return conductor;
    }

    @Override
    public Optional<Conductor> findByCpf(String cpf) {
        Optional<ConductorSchema> conductorSchema = this.conductorRepo.findByCpf(cpf);
        if (conductorSchema.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Conductor(conductorSchema.get().getId(), conductorSchema.get().getName(), conductorSchema.get().getEmail(), conductorSchema.get().getCpf(), conductorSchema.get().getPassword()));
    }

    @Override
    public Optional<Conductor> findByEmail(String email) {
        Optional<ConductorSchema> conductorSchema = this.conductorRepo.findByEmail(email);
        if (conductorSchema.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Conductor(conductorSchema.get().getId(), conductorSchema.get().getName(), conductorSchema.get().getEmail(), conductorSchema.get().getCpf(), conductorSchema.get().getPassword()));
    }

    @Override
    public Optional<Conductor> findById(UUID conductorId) {
        Optional<ConductorSchema> conductorSchema = this.conductorRepo.findById(conductorId);
        if (conductorSchema.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Conductor(conductorSchema.get().getId(), conductorSchema.get().getName(), conductorSchema.get().getEmail(), conductorSchema.get().getCpf(), conductorSchema.get().getPassword()));

    }

    @Override
    public Optional<ConductorProjectionDto> findConductorByIdWithoutPassword(UUID conductorId) {
        Optional<ConductorProjection> conductorProjection = this.conductorRepo.findConductorByIdWithoutPassword(conductorId);
        if (conductorProjection.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new ConductorProjectionDto(conductorProjection.get().getId(), conductorProjection.get().getName(), conductorProjection.get().getEmail(), conductorProjection.get().getCpf()));
    }

    @Override
    public Conductor updateConductor(Conductor conductor) {
        Optional<ConductorSchema> conductorSchema = this.conductorRepo.findById(conductor.getId());

        conductorSchema.get().setName(conductor.getName());
        conductorSchema.get().setEmail(conductor.getEmail());

        conductorRepo.save(conductorSchema.get());
        return conductor;
    }

    public UserDetails findBycpfUserDetails(String cpf) {
        return this.conductorRepo.findBycpf(cpf);
    }
}
