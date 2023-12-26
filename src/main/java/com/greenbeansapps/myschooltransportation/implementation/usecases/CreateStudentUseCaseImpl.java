package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateStudentUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;

import java.util.Optional;
import java.util.UUID;

public class CreateStudentUseCaseImpl implements CreateStudentUseCase {
    private final StudentRepository studentRepo;

    public CreateStudentUseCaseImpl(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }
    @Override
    public Student execute(String name, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration, Conductor conductor, Responsible responsible, Address address) {
        var newStudent = new Student(UUID.randomUUID(), name, school, grade, monthlyPayment, monthlyPaymentExpiration, conductor, responsible, address);
        this.studentRepo.create(newStudent);
        return newStudent;
    }
}
