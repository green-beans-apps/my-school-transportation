package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ResponsibleSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IResponsibleRepositoryJPA extends JpaRepository<ResponsibleSchema, UUID> {
    Optional<ResponsibleSchema> findByEmail(String email);
    Optional<ResponsibleSchema> findByPhoneNumber(String phoneNumber);
}
