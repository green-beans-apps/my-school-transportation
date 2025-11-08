package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.ParametersConductor;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateParametersConductorRequest;

public interface CreateParametersConductorUseCase {
    public ParametersConductor execute(CreateParametersConductorRequest data);
}
