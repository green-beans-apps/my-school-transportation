package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.GetConductorByIdResponse;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IConductorRepositoryJPA;
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
        this.conductorRepo.save(conductor);
        return conductor;
    }

    @Override
    public Optional<Conductor> findByCpf(String cpf) {
        Optional<Conductor> conductor = this.conductorRepo.findByCpf(cpf);
        if (conductor.isEmpty()) {
            return Optional.empty();
        }
        return conductor;
    }

    @Override
    public Optional<Conductor> findByEmail(String email) {
        Optional<Conductor> conductor = this.conductorRepo.findByEmail(email);
        if (conductor.isEmpty()) {
            return Optional.empty();
        }
        return conductor;
    }

    @Override
    public Optional<Conductor> findById(UUID conductorId) {
        Optional<Conductor> conductor = this.conductorRepo.findById(conductorId);
        if (conductor.isEmpty()) {
            return Optional.empty();
        }
        return conductor;

    }

    @Override
    public Optional<GetConductorByIdResponse> findConductorByIdWithoutPassword(UUID conductorId) {
        Optional<GetConductorByIdResponse> conductor = this.conductorRepo.findConductorByIdWithoutPassword(conductorId);
        if (conductor.isEmpty()) {
            return Optional.empty();
        }
        return conductor;
    }

    @Override
    public Conductor updateConductor(Conductor conductor) {
        Optional<Conductor> oldConductor = this.conductorRepo.findById(conductor.getId());

        oldConductor.get().setName(conductor.getName());
        oldConductor.get().setEmail(conductor.getEmail());

        conductorRepo.save(oldConductor.get());
        return conductor;
    }

    public UserDetails findBycpfUserDetails(String cpf) {
        var teste = this.conductorRepo.findBycpf(cpf);
        return this.conductorRepo.findBycpf(cpf);
    }
}
