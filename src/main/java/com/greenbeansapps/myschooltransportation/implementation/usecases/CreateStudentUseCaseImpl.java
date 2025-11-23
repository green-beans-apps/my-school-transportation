package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidAddressException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidConductorException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidResponsibleException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidTransportationTypeException;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateStudentUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateStudentRequest;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.*;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateStudentUseCaseImpl implements CreateStudentUseCase {
    private final StudentRepository studentRepo;
    private final ResponsibleRepository responsibleRepo;
    private final AddressRepository addressRepo;
    private final ConductorRepository conductorRepo;

    public CreateStudentUseCaseImpl(StudentRepository studentRepo, ResponsibleRepository responsibleRepo, AddressRepository addressRepo, ConductorRepository conductorRepo) {
        this.studentRepo = studentRepo;
        this.responsibleRepo = responsibleRepo;
        this.addressRepo = addressRepo;
        this.conductorRepo = conductorRepo;
    }

    @Override
    public Student execute(CreateStudentRequest data) {

        Optional<Responsible> responsible = this.responsibleRepo.findById(data.responsibleId());
        if (responsible.isEmpty()) {
            throw new InvalidResponsibleException();
        }

        Optional<Address> address = this.addressRepo.findById(data.addressId());
        if (address.isEmpty()) {
            throw new InvalidAddressException();
        }

        Optional<Conductor> conductor = this.conductorRepo.findById(data.conductorId());
        if (conductor.isEmpty()) {
            throw new InvalidConductorException();
        }

        var newStudent = new Student(data.id(), data.name(), data.school(), data.grade(), data.transportationType(), data.monthlyPayment(), data.monthlyPaymentExpiration(), data.shift(), conductor.get(), responsible.get(), address.get());
        this.studentRepo.create(newStudent);
        return newStudent;
    }

}
