package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exeptions.InvalidConductorException;
import com.greenbeansapps.myschooltransportation.domain.usecases.GetAllStudentsByConductorIdUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetAllStudentsByConductorIdUseCaseImpl implements GetAllStudentsByConductorIdUseCase {

    private final StudentRepository studentRepo;
    private final ConductorRepository conductorRepo;


    public GetAllStudentsByConductorIdUseCaseImpl(StudentRepository studentRepo, ConductorRepository conductorRepo) {
        this.studentRepo = studentRepo;
        this.conductorRepo = conductorRepo;
    }

    @Override
    public List<Student> execute(UUID conductorId) {
        Optional<Conductor> conductor = this.conductorRepo.findById(conductorId);
        if (conductor.isEmpty()) {
            throw new InvalidConductorException();
        }
        return this.studentRepo.findAllByConductorId(conductorId);
    }
}
