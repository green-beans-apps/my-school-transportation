package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetAllStudentsByConductorIdUseCaseImplTest {

    @Mock
    StudentRepository studentRepo;
    @Mock
    ConductorRepository conductorRepo;
    @InjectMocks
    GetAllStudentsByConductorIdUseCaseImpl getAllStudentsByConductorIdUseCase;

    Conductor mockConductor;
    Address mockAddress;
    Responsible mockResponsible;
    Student mockFirstStudent, mockSecondStudent;

    @BeforeEach
    void setup() {
        mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;
        mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", 123);
        mockResponsible = new Responsible(UUID.randomUUID(), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
        mockFirstStudent = new Student(UUID.randomUUID(), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", 140,
                "04", mockConductor, mockResponsible, mockAddress);
        mockSecondStudent = new Student(UUID.randomUUID(), "Ueslei Nogueira", "Colégio ETE Porto Digital", "3° Ano (Médio)", 140,
                "02", mockConductor, mockResponsible, mockAddress);
    }

    @Test
    @DisplayName("Deve retornar todos os alunos vinculados ao id do condutor")
    void case1() {
        //Arrange
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.of(mockConductor));
        Mockito.when(studentRepo.findAllByConductorId(mockConductor.getId())).thenReturn(List.of(mockFirstStudent, mockSecondStudent));

        //Act
        var returnStudents = getAllStudentsByConductorIdUseCase.execute(mockConductor.getId());

        //Assertion
        // checando retornos da consulta realizada
        Student firstStudent = returnStudents.get(0);
        assertEquals(mockFirstStudent.getName(), firstStudent.getName());
        assertEquals(mockFirstStudent.getSchool(), firstStudent.getSchool());
        assertEquals(mockFirstStudent.getGrade(), firstStudent.getGrade());
        assertEquals(mockFirstStudent.getMonthlyPayment(), firstStudent.getMonthlyPayment());
        assertEquals(mockFirstStudent.getMonthlyPaymentExpiration(), firstStudent.getMonthlyPaymentExpiration());
        assertEquals(mockFirstStudent.getConductor(), firstStudent.getConductor());
        assertEquals(mockFirstStudent.getResponsible(), firstStudent.getResponsible());
        assertEquals(mockFirstStudent.getAddress(), firstStudent.getAddress());
        assertDoesNotThrow(() -> UUID.fromString(firstStudent.getId().toString()));

        Student secondStudent = returnStudents.get(1);
        assertEquals(mockSecondStudent.getName(), secondStudent.getName());
        assertEquals(mockSecondStudent.getSchool(), secondStudent.getSchool());
        assertEquals(mockSecondStudent.getGrade(), secondStudent.getGrade());
        assertEquals(mockSecondStudent.getMonthlyPayment(), secondStudent.getMonthlyPayment());
        assertEquals(mockSecondStudent.getMonthlyPaymentExpiration(), secondStudent.getMonthlyPaymentExpiration());
        assertEquals(mockSecondStudent.getConductor(), secondStudent.getConductor());
        assertEquals(mockSecondStudent.getResponsible(), secondStudent.getResponsible());
        assertEquals(mockSecondStudent.getAddress(), secondStudent.getAddress());
        assertDoesNotThrow(() -> UUID.fromString(secondStudent.getId().toString()));
    }
}
