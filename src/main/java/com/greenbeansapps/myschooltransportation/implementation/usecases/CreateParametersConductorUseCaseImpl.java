package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.ParametersConductor;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ConductorNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateParametersConductorUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateParametersConductorRequest;
import com.greenbeansapps.myschooltransportation.infra.repositories.IConductorRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.IParametersConductorJPA;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateParametersConductorUseCaseImpl implements CreateParametersConductorUseCase {

    private IParametersConductorJPA parametersConductorRepo;
    private IConductorRepositoryJPA conductorRepo;

    public CreateParametersConductorUseCaseImpl(IParametersConductorJPA parametersConductorRepo, IConductorRepositoryJPA conductorRepo) {
        this.parametersConductorRepo = parametersConductorRepo;
        this.conductorRepo = conductorRepo;
    }

    @Override
    public ParametersConductor execute(CreateParametersConductorRequest data) {

        Optional<Conductor> conductor = conductorRepo.findById(data.conductorId());

        if (conductor.isEmpty()) {
            throw new ConductorNotFoundException();
        }

        ParametersConductor parametersConductor = new ParametersConductor(conductor.get(), data.percentContractTermination());
        parametersConductorRepo.save(parametersConductor);
        return parametersConductor;
    }
}
