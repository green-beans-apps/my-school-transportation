package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    public Student create(Student student);
    public List<StudentProjection> findAllByConductorId(UUID conductorId);
    public Optional<StudentProjection> findStudentByIdWithoutConductor(UUID studentId);
    public Optional<Student> findById(UUID studentId);
    public Boolean deleteStudent(UUID studentId);
    public Student updateStudent(Student student);
}
