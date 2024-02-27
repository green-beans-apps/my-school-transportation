package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;

import java.util.List;
import java.util.UUID;

public interface GetAllStudentsByConductorIdUseCase {
    List<StudentWithPayments> execute(UUID conductorId);
}
