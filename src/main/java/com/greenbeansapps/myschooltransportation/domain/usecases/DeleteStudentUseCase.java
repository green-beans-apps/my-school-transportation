package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;

import java.util.UUID;

public interface DeleteStudentUseCase {
    public Boolean execute(UUID studentId);
}
