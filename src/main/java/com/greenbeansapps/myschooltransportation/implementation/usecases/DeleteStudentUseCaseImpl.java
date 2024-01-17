package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.exeptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.DeleteStudentUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteStudentUseCaseImpl implements DeleteStudentUseCase {
    private final StudentRepository studentRepo;

    public DeleteStudentUseCaseImpl(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public void execute(UUID studentId) {
        Boolean deleteStudent = studentRepo.deleteStudent(studentId);
        if (!deleteStudent) {
            throw new StudentNotFoundException();
        }

        studentRepo.deleteStudent(studentId);
    }
}
