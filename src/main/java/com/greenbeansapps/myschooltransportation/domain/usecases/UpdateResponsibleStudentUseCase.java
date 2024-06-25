package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateResponsibleRequest;

import java.util.UUID;

public interface UpdateResponsibleStudentUseCase {
    public Responsible execute(UpdateResponsibleRequest data);
}
