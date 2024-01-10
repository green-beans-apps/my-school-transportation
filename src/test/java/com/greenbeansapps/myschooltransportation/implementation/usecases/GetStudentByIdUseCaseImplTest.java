package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exeptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GetStudentByIdUseCaseImplTest {
    @Mock
    StudentRepository studentRepo;
    @InjectMocks
    GetStudentByIdUseCaseImpl getStudentByIdUseCase;

    Conductor mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
    Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", 123);;
    Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
    Student mockStudent = new Student(UUID.randomUUID(), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", 140,
            "04", mockConductor, mockResponsible, mockAddress);

    @Test
    @DisplayName("Deve retornar um estudante por id")
    void case1() {
        //Arrange
        Mockito.when(studentRepo.findById(mockStudent.getId())).thenReturn(Optional.of(mockStudent));

        //Act
        var returnStudent = getStudentByIdUseCase.execute(mockStudent.getId());

        //Assertion
        // checando retornos da consulta realizada
        assertEquals(mockStudent.getName(), returnStudent.getName());
        assertEquals(mockStudent.getSchool(), returnStudent.getSchool());
        assertEquals(mockStudent.getGrade(), returnStudent.getGrade());
        assertEquals(mockStudent.getMonthlyPayment(), returnStudent.getMonthlyPayment());
        assertEquals(mockStudent.getMonthlyPaymentExpiration(), returnStudent.getMonthlyPaymentExpiration());
        assertEquals(mockStudent.getConductor(), returnStudent.getConductor());
        assertEquals(mockStudent.getResponsible(), returnStudent.getResponsible());
        assertEquals(mockStudent.getAddress(), returnStudent.getAddress());
        assertDoesNotThrow(() -> UUID.fromString(returnStudent.getId().toString()));
    }

    @Test
    @DisplayName("Não deve retornar um estudante com id inválido")
    void case2() {
        //Arrange
        Mockito.when(studentRepo.findById(mockStudent.getId())).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
           getStudentByIdUseCase.execute(mockStudent.getId());
        });
    }
}
