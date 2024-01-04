package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IStudentRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.StudentSchema;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentRepositoryJPA implements StudentRepository {

    private final IStudentRepositoryJPA studentRepo;

    public StudentRepositoryJPA(IStudentRepositoryJPA studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public Student create(Student student) {
        var newStudent = new StudentSchema();
        BeanUtils.copyProperties(student, newStudent);
        this.studentRepo.save(newStudent);
        return student;
    }

    @Override
    public List<Student> findAllByConductorId(UUID conductorId) {
        List<StudentSchema> studentsSchema = this.studentRepo.findAllByConductorId(conductorId);
        if (studentsSchema.isEmpty()) {
            return null;
        }
        List<Student> students = new ArrayList<>();
        for (StudentSchema studentSchema : studentsSchema) {
            students.add(new Student(studentSchema.getId(), studentSchema.getName(), studentSchema.getSchool(),
                    studentSchema.getGrade(), studentSchema.getMonthlyPayment(), studentSchema.getMonthlyPaymentExpiration(),
                    studentSchema.getConductor(), studentSchema.getResponsible(), studentSchema.getAddress()));
        }
        return students;
    }

    @Override
    public Optional<Student> findById(UUID studentId) {
        Optional<StudentSchema> studentSchema = this.studentRepo.findById(studentId);
        if (studentSchema.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Student(studentSchema.get().getId(), studentSchema.get().getName(), studentSchema.get().getSchool(),
                studentSchema.get().getGrade(), studentSchema.get().getMonthlyPayment(), studentSchema.get().getMonthlyPaymentExpiration(),
                studentSchema.get().getConductor(), studentSchema.get().getResponsible(), studentSchema.get().getAddress()));
    }
}
