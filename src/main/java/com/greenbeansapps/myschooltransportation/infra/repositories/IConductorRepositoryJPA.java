package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ConductorSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface IConductorRepositoryJPA extends JpaRepository<ConductorSchema, UUID> {
    Optional<ConductorSchema> findByCpf(String cpf);
    Optional<ConductorSchema> findByEmail(String email);

    UserDetails findBycpf(String cpf);
}
