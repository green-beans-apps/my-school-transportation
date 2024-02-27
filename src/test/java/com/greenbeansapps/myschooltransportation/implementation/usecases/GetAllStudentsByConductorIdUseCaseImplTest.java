package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.*;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.enums.Shift;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidConductorException;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.StudentWithPayments;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GetAllStudentsByConductorIdUseCaseImplTest {

    @Mock
    StudentRepository studentRepo;
    @Mock
    ConductorRepository conductorRepo;
    @Mock
    PaymentRepository paymentRepo;

    @InjectMocks
    GetAllStudentsByConductorIdUseCaseImpl getAllStudentsByConductorIdUseCase;

    List<Payment> payments = new ArrayList<>(Arrays.asList(
            new Payment(UUID.fromString("99b7d061-1ad2-46de-aad5-9da1376fb510"), "18", Months.JANEIRO, new Student()),
            new Payment(UUID.fromString("99b7d061-1ad2-46de-aad5-9da1376fb511"), "18", Months.FEVEREIRO, new Student())
    ));


    Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
    Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", "123");;
    Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
    StudentWithPayments mockFirstStudent = new StudentWithPayments(UUID.fromString("296c5a89-5a29-4549-bb48-f57ff7f04619"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA, Shift.MANHA, 140.90,
            18, mockResponsible, mockAddress, payments);
    StudentWithPayments mockSecondStudent = new StudentWithPayments(UUID.fromString("296c5a89-5a29-4549-bb48-f57ff7f04610"), "Ueslei Nogueira", "Colégio ETE Porto Digital", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA, Shift.MANHA, 140.90,
            18, mockResponsible, mockAddress, payments);

    @Test
    @DisplayName("Deve retornar todos os alunos vinculados ao id do condutor")
    void case1() {
        //Arrange
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.of(mockConductor));
        Mockito.when(studentRepo.findAllByConductorIdWithPayments(mockConductor.getId())).thenReturn(List.of(mockFirstStudent, mockSecondStudent));

        //Act
        var returnStudents = getAllStudentsByConductorIdUseCase.execute(mockConductor.getId());

        //Assertion
        // checando retornos da consulta realizada
        StudentWithPayments firstStudent = returnStudents.get(0);
        assertThat(mockFirstStudent)
                .usingRecursiveComparison()
                .isEqualTo(firstStudent);

        StudentWithPayments secondStudent = returnStudents.get(1);
        assertThat(mockSecondStudent)
                .usingRecursiveComparison()
                .isEqualTo(secondStudent);
    }

    @Test
    @DisplayName("Não deve retornar estudante com id do condutor inválido")
    void case2() {
        //Arrange
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(InvalidConductorException.class, () -> {
            getAllStudentsByConductorIdUseCase.execute(mockConductor.getId());
        });
    }
}
