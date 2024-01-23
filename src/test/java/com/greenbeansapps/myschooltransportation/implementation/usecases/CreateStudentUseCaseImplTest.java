package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidAddressException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidConductorException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidResponsibleException;
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

    Conductor mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
    Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);;
    Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
    Student mockStudent = new Student(UUID.randomUUID(), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", 140,
            "04", mockConductor, mockResponsible, mockAddress);

    @Test
    @DisplayName("Nao deve ser possivel criar um estudante com o endereço invalido.")
    void case1() {
        // arrange
        Mockito.when(responsibleRepo.findById(mockResponsible.getId())).thenReturn(Optional.of(mockResponsible));
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.of(mockConductor));
        Mockito.when(addressRepo.findById(mockAddress.getId())).thenReturn(Optional.empty());

        // Act e Assert
        assertThrows(InvalidAddressException.class, () -> {
            createStudentUseCase.execute(mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getMonthlyPayment(),
                    mockStudent.getMonthlyPaymentExpiration(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());
        });
    }

    @Test
    @DisplayName("Nao deve ser possivel criar um estudante com o responsavel invalido.")
    void case2() {
        // arrange
        Mockito.when(responsibleRepo.findById(mockResponsible.getId())).thenReturn(Optional.empty());
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.of(mockConductor));
        Mockito.when(addressRepo.findById(mockAddress.getId())).thenReturn(Optional.of(mockAddress));

        // Act e Assert
        assertThrows(InvalidResponsibleException.class, () -> {
            createStudentUseCase.execute(mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getMonthlyPayment(),
                    mockStudent.getMonthlyPaymentExpiration(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());
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
            createStudentUseCase.execute(mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getMonthlyPayment(),
                    mockStudent.getMonthlyPaymentExpiration(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());
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
        var newStudent = createStudentUseCase.execute(mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getMonthlyPayment(),
                mockStudent.getMonthlyPaymentExpiration(), mockConductor.getId(), mockResponsible.getId(), mockAddress.getId());

        // Assert
        // checando retornos
        assertEquals(mockStudent.getName(), newStudent.getName());
        assertEquals(mockStudent.getSchool(), newStudent.getSchool());
        assertEquals(mockStudent.getGrade(), newStudent.getGrade());
        assertEquals(mockStudent.getMonthlyPayment(), newStudent.getMonthlyPayment());
        assertEquals(mockStudent.getMonthlyPaymentExpiration(), newStudent.getMonthlyPaymentExpiration());
        assertEquals(mockStudent.getConductor(), newStudent.getConductor());
        assertEquals(mockStudent.getResponsible(), newStudent.getResponsible());
        assertEquals(mockStudent.getAddress(), newStudent.getAddress());
        assertDoesNotThrow(() -> UUID.fromString(newStudent.getId().toString()));

        //checando parâmetros passados para o repositório
        Student studentCapture = studentCaptor.getValue();
        assertEquals(mockStudent.getName(), studentCapture.getName());
        assertEquals(mockStudent.getSchool(), studentCapture.getSchool());
        assertEquals(mockStudent.getGrade(), studentCapture.getGrade());
        assertEquals(mockStudent.getMonthlyPayment(), studentCapture.getMonthlyPayment());
        assertEquals(mockStudent.getMonthlyPaymentExpiration(), studentCapture.getMonthlyPaymentExpiration());
        assertEquals(mockStudent.getConductor(), studentCapture.getConductor());
        assertEquals(mockStudent.getResponsible(), studentCapture.getResponsible());
        assertEquals(mockStudent.getAddress(), studentCapture.getAddress());
        assertDoesNotThrow(() -> UUID.fromString(studentCapture.getId().toString()));
    }
}