package com.greenbeansapps.myschooltransportation.infra.repositories;

import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentWithPaymentProjection;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.StudentSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IStudentRepositoryJPA extends JpaRepository<StudentSchema, UUID> {
    @Query("SELECT new com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection(s.id, s.name, s.school, s.grade, s.transportationType, s.shift, s.monthlyPayment, s.monthlyPaymentExpiration, s.responsible, s.address) " +
            "FROM StudentSchema s WHERE s.conductor.id = :conductorId")
    List<StudentProjection> findAllByConductorId(UUID conductorId);
    @Query("SELECT new com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection(s.id, s.name, s.school, s.grade, s.transportationType, s.shift, s.monthlyPayment, s.monthlyPaymentExpiration, s.responsible, s.address) " +
            "FROM StudentSchema s WHERE s.id = :studentId")
    Optional<StudentProjection> findStudentByIdWithoutConductor(UUID studentId);
    Optional<StudentSchema> findById(UUID studentId);

    @Query("SELECT DISTINCT s FROM StudentSchema s " +
            "LEFT JOIN FETCH s.payments " +
            "WHERE s.conductor.id = :conductorId")
    List<StudentSchema> findAllByConductorIdWithPayments(@Param("conductorId") UUID conductorId);

    @Query("SELECT s FROM StudentSchema s WHERE  s.id = :studentId")
    Optional<StudentSchema> getStudentWithPayments(@Param("studentId") UUID studentId);

}
