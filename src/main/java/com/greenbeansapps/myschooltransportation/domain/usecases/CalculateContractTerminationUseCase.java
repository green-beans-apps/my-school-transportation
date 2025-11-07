package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.implementation.dto.CalculateContractTerminationDto;

import java.util.List;

public interface CalculateContractTerminationUseCase {
    CalculateContractTerminationDto execute(List<Student> students);
}
