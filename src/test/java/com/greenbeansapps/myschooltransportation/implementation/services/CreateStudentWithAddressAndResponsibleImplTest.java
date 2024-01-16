package com.greenbeansapps.myschooltransportation.implementation.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
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

    Conductor mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");
    Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);
    Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício", "mauricio@teste.com", "(81)97314-8001");
    Student mockStudent = new Student(UUID.randomUUID(), "Danilo", "Colégio São José", "3° Ano", 140,
            "04", mockConductor, mockResponsible, mockAddress);

    CreateStudentWithAddressAndResponsible.StudentData mockStudentData = new CreateStudentWithAddressAndResponsible.StudentData(mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(),  mockStudent.getMonthlyPayment(), mockStudent.getMonthlyPaymentExpiration(), mockConductor.getId());
    CreateStudentWithAddressAndResponsible.ResponsibleData mockResponsibleData = new CreateStudentWithAddressAndResponsible.ResponsibleData(mockResponsible.getName(), mockResponsible.getEmail(), mockResponsible.getPhoneNumber());
    CreateStudentWithAddressAndResponsible.AddressData mockAddressData = new CreateStudentWithAddressAndResponsible.AddressData(mockAddress.getCity(), mockAddress.getDistrict(), mockAddress.getStreet(), mockAddress.getReferencePoint(), mockAddress.getHouseNumber());
    CreateStudentWithAddressAndResponsible.CreateStudentWithAddressAndResponsibleRequest mockRequest = new CreateStudentWithAddressAndResponsible.CreateStudentWithAddressAndResponsibleRequest(mockStudentData, mockResponsibleData, mockAddressData);

    @Test
    @DisplayName("Deve ser possivel criar um estudante com endereco e responsavel.")
    void case1() {
        Mockito.when(createAddressUseCase.execute(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(mockAddress);
        Mockito.when(createResponsibleUseCase.execute(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(mockResponsible);
        Mockito.when(createStudentUseCase.execute(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(mockStudent);

        Student createdStudent = createStudentWithAddressAndResponsible.execute(mockRequest);

        Mockito.verify(createAddressUseCase).execute(mockAddress.getCity(), mockAddress.getDistrict(), mockAddress.getStreet(), mockAddress.getReferencePoint(), mockAddress.getHouseNumber());
        Mockito.verify(createResponsibleUseCase).execute(mockResponsible.getName(), mockResponsible.getEmail(), mockResponsible.getPhoneNumber());
        Mockito.verify(createStudentUseCase).execute(mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getMonthlyPayment(), mockStudent.getMonthlyPaymentExpiration(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());

        assertEquals(mockStudent, createdStudent);
    }

}



