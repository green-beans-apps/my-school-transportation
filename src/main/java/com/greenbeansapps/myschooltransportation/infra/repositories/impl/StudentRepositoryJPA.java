package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.*;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IStudentRepositoryJPA;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class StudentRepositoryJPA implements StudentRepository {

    private final IStudentRepositoryJPA studentRepo;

    public StudentRepositoryJPA(IStudentRepositoryJPA studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public Student create(Student student) {
        this.studentRepo.save(student);
        return student;
    }

    @Override
    public Optional<Student> findById(UUID studentId) {
        Optional<Student> student = this.studentRepo.findById(studentId);
        if (student.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(student.get());
    }

    @Override
    public Boolean deleteStudent(UUID studentId) {
        Optional<Student> student = this.studentRepo.findById(studentId);
        if (student.isEmpty()) {
            return false;
        }

        studentRepo.delete(student.get());
        return true;
    }

    @Override
    public Student updateStudent(Student student) {
        Optional<Student> oldStudent = this.studentRepo.findById(student.getId());

        if (oldStudent.isEmpty()) {
            throw new StudentNotFoundException();
        }

        oldStudent.get().setName(student.getName());
        oldStudent.get().setSchool(student.getSchool());
        oldStudent.get().setGrade(student.getGrade());
        oldStudent.get().setTransportationType(student.getTransportationType());
        oldStudent.get().setMonthlyPayment(student.getMonthlyPayment());
        oldStudent.get().setMonthlyPaymentExpiration(student.getMonthlyPaymentExpiration());
        oldStudent.get().setShift(student.getShift());

        this.studentRepo.save(student);

        return student;
    }

    @Override
    public List<StudentWithPayments> findAllByConductorIdWithPayments(UUID conductorId) {
        var studentsSchema = this.studentRepo.findAllByConductorIdWithPayments(conductorId);
        List<StudentWithPayments> students = new ArrayList<StudentWithPayments>();
        for ( Student  studentS : studentsSchema) {
            var responsible = new Responsible();
            var address = new Address();
            BeanUtils.copyProperties(studentS.getResponsible(), responsible);
            BeanUtils.copyProperties(studentS.getAddress(), address);

            students.add(new StudentWithPayments(studentS.getId(), studentS.getName(), studentS.getSchool(), studentS.getGrade(), studentS.getTransportationType(), studentS.getShift(), studentS.getMonthlyPayment(), studentS.getMonthlyPaymentExpiration(), responsible, address, studentS.getPayments()));
        }

        return students;
    }

    @Override
    public Optional<StudentWithPayments> getStudentWithPayments(UUID studentId) {
        Optional<Student> student = this.studentRepo.getStudentWithPayments(studentId);

        return Optional.of(new StudentWithPayments(student.get().getId(), student.get().getName(), student.get().getSchool(), student.get().getGrade(), student.get().getTransportationType(), student.get().getShift(), student.get().getMonthlyPayment(), student.get().getMonthlyPaymentExpiration(), student.get().getResponsible(), student.get().getAddress(), student.get().getPayments()));
    }


}
