package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.StudentSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IStudentRepositoryJPA extends JpaRepository<StudentSchema, UUID> {
    List<StudentSchema> findAllByConductorId(UUID conductorId);
    Optional<StudentSchema> findById(UUID studentId);
}
