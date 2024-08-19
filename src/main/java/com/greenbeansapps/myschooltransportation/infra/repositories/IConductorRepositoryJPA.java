package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.GetConductorByIdResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface IConductorRepositoryJPA extends JpaRepository<Conductor, UUID> {
    Optional<Conductor> findByCpf(String cpf);
    Optional<Conductor> findByEmail(String email);

    @Query("SELECT new com.greenbeansapps.myschooltransportation.domain.usecases.dtos.GetConductorByIdResponse(c.id, c.name, c.email, c.cpf) " +
            "FROM Conductor c WHERE c.id = :conductorId")
    Optional<GetConductorByIdResponse> findConductorByIdWithoutPassword(UUID conductorId);

    UserDetails findBycpf(String cpf);
}
