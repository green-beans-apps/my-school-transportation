package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;

import java.util.List;
import java.util.UUID;

public interface GetAllStudentsByConductorIdUseCase {
    List<Student> execute(UUID conductorId);
}
