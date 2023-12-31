package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exeptions.InvalidConductorException;

import java.util.List;

public interface GetAllStudentsByConductorIdUseCase {
    List<Student> execute(String conductorId);
}
