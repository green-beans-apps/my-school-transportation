package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;

import java.util.Optional;

public interface ConductorRepository {
    public Conductor create(Conductor conductor);
    public Optional<Conductor> findByCpf(String cpf);
    public Optional<Conductor> findByEmail(String email);
    public Optional<Conductor> findById(String email);
}
