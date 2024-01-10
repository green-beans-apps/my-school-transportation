package com.greenbeansapps.myschooltransportation.implementation.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
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
    Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "São José", 123);
    Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício", "mauricio@teste.com", "(81)97314-8001");
    Student mockStudent = new Student(UUID.randomUUID(), "Danilo", "Colégio São José", "3° Ano", 140,
            "04", mockConductor, mockResponsible, mockAddress);

    @Test
    @DisplayName("deve ser possivel criar um estudante com endereco e responsavel.")
    void case1() {

    }

}



