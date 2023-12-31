package com.greenbeansapps.myschooltransportation.implementation.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.services.CreateStudentWithAddressAndResponsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateAddressUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateResponsibleUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateStudentUseCase;

import java.util.UUID;

public class CreateStudentWithAddressAndResponsibleImpl implements CreateStudentWithAddressAndResponsible {

    private final CreateAddressUseCase createAddressUseCase;
    private final CreateResponsibleUseCase createResponsibleUseCase;
    private final CreateStudentUseCase createStudentUseCase;

    public CreateStudentWithAddressAndResponsibleImpl(CreateAddressUseCase createAddressUseCase, CreateResponsibleUseCase createResponsibleUseCase, CreateStudentUseCase createStudentUseCase) {
        this.createAddressUseCase = createAddressUseCase;
        this.createResponsibleUseCase = createResponsibleUseCase;
        this.createStudentUseCase = createStudentUseCase;
    }

    @Override
    public Student execute(Request request) {
        Address newAddress = this.createAddressUseCase.execute(request.address().city(), request.address().district(), request.address().street(), request.address().houseNumber());
        Responsible newResponsible = this.createResponsibleUseCase.execute(request.responsible().responsibleName(), request.responsible().email(), request.responsible().phoneNumber());
        return this.createStudentUseCase.execute(request.student().studentName(), request.student().school(), request.student().grade(), request.student().monthlyPayment(), request.student().monthlyPaymentExpiration(), request.student().conductorId(), newResponsible.getId(), newAddress.getId());
    }
}
