package com.greenbeansapps.myschooltransportation.implementation.protocols.repositories;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {
    public Student create(Student student);
    public Optional<Student> findById(UUID studentId);
    public Boolean deleteStudent(UUID studentId);
    public Student updateStudent(Student student);
    public List<StudentWithPayments> findAllByConductorIdWithPayments(UUID conductorId);

    public Optional<StudentWithPayments> getStudentWithPayments(UUID studentId);
}
