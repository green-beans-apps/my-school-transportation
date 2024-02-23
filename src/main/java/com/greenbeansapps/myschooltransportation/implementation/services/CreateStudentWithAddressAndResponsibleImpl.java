package com.greenbeansapps.myschooltransportation.implementation.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.services.CreateStudentWithAddressAndResponsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateAddressUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateResponsibleUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateStudentUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateAddressRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateResponsibleRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateStudentRequest;
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
    public Student execute(CreateStudentRequest studentData, CreateAddressRequest addressData, CreateResponsibleRequest responsibleData) {
        Address newAddress = this.createAddressUseCase.execute(new CreateAddressRequest(addressData.id(), addressData.city(), addressData.district(), addressData.street(), addressData.referencePoint(), addressData.houseNumber()));
        Responsible newResponsible = this.createResponsibleUseCase.execute(new CreateResponsibleRequest( responsibleData.id(), responsibleData.name(), responsibleData.email(), responsibleData.phone()));
        return this.createStudentUseCase.execute(new CreateStudentRequest(studentData.id(),studentData.name(), studentData.school(), studentData.grade(), studentData.transportationType(), studentData.monthlyPayment(), studentData.monthlyPaymentExpiration(), studentData.shift(), studentData.conductorId(), newResponsible.getId(), newAddress.getId()));
    }
}
