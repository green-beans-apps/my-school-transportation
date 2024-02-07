package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.PaymentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionWithPaymentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.enums.Shift;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection;
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
public class GetStudentByIdUseCaseImplTest {
    @Mock
    StudentRepository studentRepo;
    @Mock
    PaymentRepository paymentRepo;

    @InjectMocks
    GetStudentByIdUseCaseImpl getStudentByIdUseCase;

    Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);;
    Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
    List<PaymentProjectionDto> paymentList = new ArrayList<>(
            List.of(
                    new PaymentProjectionDto(UUID.fromString("99b7d061-1ad2-46de-aad5-9da1376fb510"), "18", Months.JANEIRO),
                    new PaymentProjectionDto(UUID.fromString("99b7d061-1ad2-46de-aad5-9da1376fb511"), "18", Months.FEVEREIRO)
            )
    );
    StudentProjectionWithPaymentProjectionDto mockStudent = new StudentProjectionWithPaymentProjectionDto(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA, Shift.MANHA,140,
            "18", mockResponsible, mockAddress, paymentList);

    @Test
    @DisplayName("Deve retornar um estudante por id")
    void case1() {
        //Arrange
        Mockito.when(studentRepo.findStudentByIdWithoutConductor(mockStudent.getId())).thenReturn(Optional.of(mockStudent));
        Mockito.when(paymentRepo.findAllPaymentByStudentId(mockStudent.getId())).thenReturn(paymentList);
        //Act
        var returnStudent = getStudentByIdUseCase.execute(mockStudent.getId());

        //Assertion
        // checando retornos da consulta realizada
        assertThat(returnStudent)
                .usingRecursiveComparison()
                .isEqualTo(mockStudent);
    }

    @Test
    @DisplayName("Não deve retornar um estudante com id inválido")
    void case2() {
        //Arrange
        Mockito.when(studentRepo.findStudentByIdWithoutConductor(mockStudent.getId())).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(StudentNotFoundException.class, () -> {
           getStudentByIdUseCase.execute(mockStudent.getId());
        });
    }
}
