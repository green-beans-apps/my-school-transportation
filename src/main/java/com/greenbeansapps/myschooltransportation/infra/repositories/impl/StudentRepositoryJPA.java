package com.greenbeansapps.myschooltransportation.infra.repositories.impl;

import com.greenbeansapps.myschooltransportation.domain.entities.*;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.IStudentRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.*;
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
        var newStudent = new StudentSchema();
        BeanUtils.copyProperties(student, newStudent);

        var newConductor = new ConductorSchema();
        BeanUtils.copyProperties(student.getConductor(), newConductor);
        newStudent.setConductor(newConductor);

        var newResponsible = new ResponsibleSchema();
        BeanUtils.copyProperties(student.getResponsible(), newResponsible);
        newStudent.setResponsible(newResponsible);

        var newAddress = new AddressSchema();
        BeanUtils.copyProperties(student.getAddress(), newAddress);
        newStudent.setAddress(newAddress);

        this.studentRepo.save(newStudent);
        return student;
    }

    @Override
    public Optional<Student> findById(UUID studentId) {
        Optional<StudentSchema> studentSchema = this.studentRepo.findById(studentId);
        if (studentSchema.isEmpty()) {
            return Optional.empty();
        }

        var newConductor = new Conductor();
        var newResponsible = new Responsible();
        var newAddress = new Address();

        BeanUtils.copyProperties(studentSchema.get().getConductor(), newConductor);
        BeanUtils.copyProperties(studentSchema.get().getResponsible(), newResponsible);
        BeanUtils.copyProperties(studentSchema.get().getAddress(), newAddress);

        return Optional.of(new Student(studentSchema.get().getId(), studentSchema.get().getName(), studentSchema.get().getSchool(),
                studentSchema.get().getGrade(), studentSchema.get().getTransportationType().toString(), studentSchema.get().getMonthlyPayment(), studentSchema.get().getMonthlyPaymentExpiration(), studentSchema.get().getShift().toString(),
                newConductor, newResponsible, newAddress));
    }

    @Override
    public Boolean deleteStudent(UUID studentId) {
        Optional<StudentSchema> studentSchema = this.studentRepo.findById(studentId);
        if (studentSchema.isEmpty()) {
            return false;
        }

        studentRepo.delete(studentSchema.get());
        return true;
    }

    @Override
    public Student updateStudent(Student student) {
        Optional<StudentSchema> studentSchema = this.studentRepo.findById(student.getId());

        if (studentSchema.isEmpty()) {
            throw new StudentNotFoundException();
        }

        studentSchema.get().setName(student.getName());
        studentSchema.get().setSchool(student.getSchool());
        studentSchema.get().setGrade(student.getGrade());
        studentSchema.get().setTransportationType(student.getTransportationType());
        studentSchema.get().setMonthlyPayment(student.getMonthlyPayment());
        studentSchema.get().setMonthlyPaymentExpiration(student.getMonthlyPaymentExpiration());
        studentSchema.get().setShift(student.getShift());

        this.studentRepo.save(studentSchema.get());

        return student;
    }

    @Override
    public List<StudentWithPayments> findAllByConductorIdWithPayments(UUID conductorId) {
        var studentsSchema = this.studentRepo.findAllByConductorIdWithPayments(conductorId);
        List<StudentWithPayments> students = new ArrayList<StudentWithPayments>();
        for ( StudentSchema  studentS : studentsSchema) {
            var responsible = new Responsible();
            var address = new Address();
            BeanUtils.copyProperties(studentS.getResponsible(), responsible);
            BeanUtils.copyProperties(studentS.getAddress(), address);

            List<Payment> payments = new ArrayList<Payment>();
            for (PaymentSchema paymentSchema : studentS.getPayments()) {
                var newPayment = new Payment();
                BeanUtils.copyProperties(paymentSchema, newPayment);
                payments.add(newPayment);
            }
            students.add(new StudentWithPayments(studentS.getId(), studentS.getName(), studentS.getSchool(), studentS.getGrade(), studentS.getTransportationType(), studentS.getShift(), studentS.getMonthlyPayment(), studentS.getMonthlyPaymentExpiration(), responsible, address, payments));
        }

        return students;
    }

    @Override
    public Optional<StudentWithPayments> getStudentWithPayments(UUID studentId) {
        Optional<StudentSchema> studentSchema = this.studentRepo.getStudentWithPayments(studentId);
        var responsible = new Responsible();
        var address = new Address();
        BeanUtils.copyProperties(studentSchema.get().getResponsible(), responsible);
        BeanUtils.copyProperties(studentSchema.get().getAddress(), address);

        List<Payment> payments = new ArrayList<Payment>();
        for (PaymentSchema paymentSchema : studentSchema.get().getPayments()) {
            var newPayment = new Payment();
            BeanUtils.copyProperties(paymentSchema, newPayment);
            payments.add(newPayment);
        }

        return Optional.of(new StudentWithPayments(studentSchema.get().getId(), studentSchema.get().getName(), studentSchema.get().getSchool(), studentSchema.get().getGrade(), studentSchema.get().getTransportationType(), studentSchema.get().getShift(), studentSchema.get().getMonthlyPayment(), studentSchema.get().getMonthlyPaymentExpiration(), responsible, address, payments));
    }


}
