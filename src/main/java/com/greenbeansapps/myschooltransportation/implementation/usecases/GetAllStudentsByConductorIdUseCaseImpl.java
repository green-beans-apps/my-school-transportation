    package com.greenbeansapps.myschooltransportation.implementation.usecases;

    import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidConductorException;
    import com.greenbeansapps.myschooltransportation.domain.usecases.GetAllStudentsByConductorIdUseCase;
    import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;
    import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
    import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
    import org.springframework.stereotype.Service;

    import java.util.*;

    @Service
    public class GetAllStudentsByConductorIdUseCaseImpl implements GetAllStudentsByConductorIdUseCase {

        private final StudentRepository studentRepo;

        private final ConductorRepository conductorRepo;

        public GetAllStudentsByConductorIdUseCaseImpl(StudentRepository studentRepo, ConductorRepository conductorRepo) {
            this.studentRepo = studentRepo;
            this.conductorRepo = conductorRepo;
        }

        @Override
        public List<StudentWithPayments> execute(UUID conductorId) {
            var conductor = conductorRepo.findById(conductorId);
            if(conductor.isEmpty()) {
                throw new InvalidConductorException();
            }

           return this.studentRepo.findAllByConductorIdWithPayments(conductorId);
        }
    }
