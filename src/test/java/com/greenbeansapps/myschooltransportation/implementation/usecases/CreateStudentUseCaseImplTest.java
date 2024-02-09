package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.*;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CreateStudentUseCaseImplTest {

    @Mock
    StudentRepository studentRepo;
    @Mock
    ResponsibleRepository responsibleRepo;
    @Mock
    AddressRepository addressRepo;
    @Mock
    ConductorRepository conductorRepo;
    @InjectMocks
    CreateStudentUseCaseImpl createStudentUseCase;

    Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
    Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);;
    Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
    Student mockStudent = new Student(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA.toString(), 140,
            4,"manha", mockConductor, mockResponsible, mockAddress);

    @Test
    @DisplayName("Nao deve ser possivel criar um estudante com o endereço invalido.")
    void case1() {
        // arrange
        Mockito.when(responsibleRepo.findById(mockResponsible.getId())).thenReturn(Optional.of(mockResponsible));
        Mockito.when(addressRepo.findById(Mockito.any())).thenReturn(Optional.empty());

        // Act e Assert
        assertThrows(InvalidAddressException.class, () -> {
            createStudentUseCase.execute(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getTransportationType().toString(), mockStudent.getMonthlyPayment(),
                    mockStudent.getMonthlyPaymentExpiration(), mockStudent.getShift().toString(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());
        });
    }

    @Test
    @DisplayName("Nao deve ser possivel criar um estudante com o responsavel invalido.")
    void case2() {
        // arrange
        Mockito.when(responsibleRepo.findById(mockResponsible.getId())).thenReturn(Optional.empty());

        // Act e Assert
        assertThrows(InvalidResponsibleException.class, () -> {
            createStudentUseCase.execute(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getTransportationType().toString(), mockStudent.getMonthlyPayment(),
                    mockStudent.getMonthlyPaymentExpiration(), mockStudent.getShift().toString(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());
        });
    }

    @Test
    @DisplayName("Nao deve ser possivel criar um estudante com o condutor invalido.")
    void case3() {
        // arrange
        Mockito.when(responsibleRepo.findById(mockResponsible.getId())).thenReturn(Optional.of(mockResponsible));
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.empty());
        Mockito.when(addressRepo.findById(mockAddress.getId())).thenReturn(Optional.of(mockAddress));

        // Act e Assert
        assertThrows(InvalidConductorException.class, () -> {
            createStudentUseCase.execute(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getTransportationType().toString(), mockStudent.getMonthlyPayment(),
                    mockStudent.getMonthlyPaymentExpiration(), mockStudent.getShift().toString(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());
        });
    }

    @Test
    @DisplayName("Deve ser possivel criar um estudante com todos os dados válidos.")
    void case4() {
        // arrange
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);

        Mockito.when(responsibleRepo.findById(mockResponsible.getId())).thenReturn(Optional.of(mockResponsible));
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.of(mockConductor));
        Mockito.when(addressRepo.findById(mockAddress.getId())).thenReturn(Optional.of(mockAddress));
        Mockito.when(studentRepo.create(studentCaptor.capture())).thenReturn(mockStudent);

        // Act
        var newStudent = createStudentUseCase.execute(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getTransportationType().toString(), mockStudent.getMonthlyPayment(),
                mockStudent.getMonthlyPaymentExpiration(), mockStudent.getShift().toString(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());

        // Assert
        // checando retornos
        assertThat(newStudent)
                .usingRecursiveComparison()
                .isEqualTo(mockStudent);

        //checando parâmetros passados para o repositório
        Student studentCapture = studentCaptor.getValue();
        assertThat(studentCapture)
                .usingRecursiveComparison()
                .isEqualTo(mockStudent);
    }

    @Test
    @DisplayName("Nao deve ser possivel cadastrar um estudante com o tipo de transporte invalido")
    void case5() {
        // arrange
        Mockito.when(responsibleRepo.findById(mockResponsible.getId())).thenReturn(Optional.of(mockResponsible));
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.of(mockConductor));
        Mockito.when(addressRepo.findById(mockAddress.getId())).thenReturn(Optional.of(mockAddress));

        assertThrows(InvalidTransportationTypeException.class, () -> {
            createStudentUseCase.execute(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), "volt", mockStudent.getMonthlyPayment(),
                    mockStudent.getMonthlyPaymentExpiration(), mockStudent.getShift().toString(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());
        });
    }

    @Test
    @DisplayName("Nao deve ser possivel atualizar um estudante com uma data que nao esteja entre 1 e 28")
    void case6() {
        Mockito.when(responsibleRepo.findById(mockResponsible.getId())).thenReturn(Optional.of(mockResponsible));
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.of(mockConductor));
        Mockito.when(addressRepo.findById(mockAddress.getId())).thenReturn(Optional.of(mockAddress));

        assertThrows(InvalidMonthlyPaymentExpirationException.class, () -> {
            createStudentUseCase.execute(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), "volta", mockStudent.getMonthlyPayment(),
                    29, mockStudent.getShift().toString(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());
        });
    }
}