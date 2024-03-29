package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.infra.repositories.projection.ConductorProjection;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ConductorSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface IConductorRepositoryJPA extends JpaRepository<ConductorSchema, UUID> {
    Optional<ConductorSchema> findByCpf(String cpf);
    Optional<ConductorSchema> findByEmail(String email);

    @Query("SELECT new com.greenbeansapps.myschooltransportation.infra.repositories.projection.ConductorProjection(c.id, c.name, c.email, c.cpf) " +
            "FROM ConductorSchema c WHERE c.id = :conductorId")
    Optional<ConductorProjection> findConductorByIdWithoutPassword(UUID conductorId);

    UserDetails findBycpf(String cpf);
}
