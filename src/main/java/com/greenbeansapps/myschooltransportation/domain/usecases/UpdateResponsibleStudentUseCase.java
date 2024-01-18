package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;

import java.util.UUID;

public interface UpdateResponsibleStudentUseCase {
    public Responsible execute(UUID studentId, String name, String email, String phoneNumber);
}
