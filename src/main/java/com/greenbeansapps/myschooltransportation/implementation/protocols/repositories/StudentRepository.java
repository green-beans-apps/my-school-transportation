package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    public Student create(Student student);
    public List<Student> findAllByConductorId(UUID conductorId);
    public Optional<Student> findById(UUID studentId);
}
