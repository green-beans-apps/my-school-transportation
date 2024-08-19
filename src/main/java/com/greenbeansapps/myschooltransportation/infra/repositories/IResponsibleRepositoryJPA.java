package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IResponsibleRepositoryJPA extends JpaRepository<Responsible, UUID> {
    Optional<Responsible> findByEmail(String email);
    Optional<Responsible> findByphone(String phone);
}
