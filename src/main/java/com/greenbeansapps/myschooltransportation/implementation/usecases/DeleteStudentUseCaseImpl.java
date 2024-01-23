package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.DeleteStudentUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteStudentUseCaseImpl implements DeleteStudentUseCase {
    private final StudentRepository studentRepo;
    private final AddressRepository addressRepo;
    private final ResponsibleRepository responsibleRepo;

    public DeleteStudentUseCaseImpl(StudentRepository studentRepo, AddressRepository addressRepo, ResponsibleRepository responsibleRepo) {
        this.studentRepo = studentRepo;
        this.addressRepo = addressRepo;
        this.responsibleRepo = responsibleRepo;
    }

    @Override
    public Boolean execute(UUID studentId) {
        Optional<Student> getStudent = studentRepo.findById(studentId);
        if (getStudent.isEmpty()) {
            throw new StudentNotFoundException();
        }

        studentRepo.deleteStudent(getStudent.get().getId());
        addressRepo.deleteAddress(getStudent.get().getAddress().getId());
        responsibleRepo.deleteResponsible(getStudent.get().getResponsible().getId());
        return true;
    }
}
