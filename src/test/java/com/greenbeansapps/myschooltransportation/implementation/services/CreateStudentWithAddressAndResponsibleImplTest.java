package com.greenbeansapps.myschooltransportation.implementation.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.services.CreateStudentWithAddressAndResponsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateAddressUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateResponsibleUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateStudentUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateStudentWithAddressAndResponsibleImplTest {

    @Mock
    private CreateAddressUseCase createAddressUseCase;
    @Mock
    private CreateResponsibleUseCase createResponsibleUseCase;
    @Mock
    private CreateStudentUseCase createStudentUseCase;

    @InjectMocks
    CreateStudentWithAddressAndResponsibleImpl createStudentWithAddressAndResponsible;

    Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
    Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);;
    Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
    Student mockStudent = new Student(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA.toString(), 140,
            "04", mockConductor, mockResponsible, mockAddress);
    CreateStudentWithAddressAndResponsible.StudentData mockStudentData = new CreateStudentWithAddressAndResponsible.StudentData(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getTransportationType().toString(),  mockStudent.getMonthlyPayment(), mockStudent.getMonthlyPaymentExpiration(), mockConductor.getId());
    CreateStudentWithAddressAndResponsible.ResponsibleData mockResponsibleData = new CreateStudentWithAddressAndResponsible.ResponsibleData(mockResponsible.getId(), mockResponsible.getName(), mockResponsible.getEmail(), mockResponsible.getphone());
    CreateStudentWithAddressAndResponsible.AddressData mockAddressData = new CreateStudentWithAddressAndResponsible.AddressData(mockAddress.getId(), mockAddress.getCity(), mockAddress.getDistrict(), mockAddress.getStreet(), mockAddress.getReferencePoint(), mockAddress.getHouseNumber());
    CreateStudentWithAddressAndResponsible.CreateStudentWithAddressAndResponsibleRequest mockRequest = new CreateStudentWithAddressAndResponsible.CreateStudentWithAddressAndResponsibleRequest(mockStudentData, mockResponsibleData, mockAddressData);

    @Test
    @DisplayName("Deve ser possivel criar um estudante com endereco e responsavel.")
    void case1() {
        Mockito.when(createAddressUseCase.execute(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(mockAddress);
        Mockito.when(createResponsibleUseCase.execute(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(mockResponsible);
        Mockito.when(createStudentUseCase.execute(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(mockStudent);

        Student createdStudent = createStudentWithAddressAndResponsible.execute(mockRequest);

        Mockito.verify(createAddressUseCase).execute(mockAddress.getId(), mockAddress.getCity(), mockAddress.getDistrict(), mockAddress.getStreet(), mockAddress.getReferencePoint(), mockAddress.getHouseNumber());
        Mockito.verify(createResponsibleUseCase).execute(mockResponsible.getId(), mockResponsible.getName(), mockResponsible.getEmail(), mockResponsible.getphone());
        Mockito.verify(createStudentUseCase).execute(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getTransportationType().toString(), mockStudent.getMonthlyPayment(), mockStudent.getMonthlyPaymentExpiration(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());

        assertEquals(mockStudent, createdStudent);
    }

}



