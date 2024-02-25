package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidResponsibleException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.UpdateResponsibleStudentUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateResponsibleRequest;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateResponsibleStudentUseCaseImpl implements UpdateResponsibleStudentUseCase {

    private final ResponsibleRepository responsibleRepo;
    private final StudentRepository studentRepo;

    public UpdateResponsibleStudentUseCaseImpl(ResponsibleRepository responsibleRepo, StudentRepository studentRepo) {
        this.responsibleRepo = responsibleRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public Responsible execute(UpdateResponsibleRequest data ) {
        Optional<Student> getStudent = this.studentRepo.findById(data.studentId());
        if(getStudent.isEmpty()) {
            throw new StudentNotFoundException();
        }

        Optional<Responsible> getResponsible = this.responsibleRepo.findById(getStudent.get().getResponsible().getId());

        getResponsible.get().setName(data.name());
        getResponsible.get().setEmail(data.email());
        getResponsible.get().setPhone(data.phone());

        return responsibleRepo.updateResponsible(getResponsible.get());
    }
}
