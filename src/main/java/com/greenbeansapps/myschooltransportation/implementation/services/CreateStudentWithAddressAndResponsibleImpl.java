package com.greenbeansapps.myschooltransportation.implementation.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.services.CreateStudentWithAddressAndResponsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateAddressUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateResponsibleUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateStudentUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
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
    public Student execute(CreateStudentWithAddressAndResponsibleRequest request) {
        Address newAddress = this.createAddressUseCase.execute(request.address().id(), request.address().city(), request.address().district(), request.address().street(), request.address().referencePoint(), request.address().houseNumber());
        Responsible newResponsible = this.createResponsibleUseCase.execute(request.responsible().id(), request.responsible().responsibleName(), request.responsible().email(), request.responsible().phone());
        return this.createStudentUseCase.execute(request.student().id(), request.student().studentName(), request.student().school(), request.student().grade(), request.student().transportationType(), request.student().monthlyPayment(), request.student().monthlyPaymentExpiration(), request.student().shift(), request.student().conductorId(), newResponsible.getId(), newAddress.getId());
    }
}
