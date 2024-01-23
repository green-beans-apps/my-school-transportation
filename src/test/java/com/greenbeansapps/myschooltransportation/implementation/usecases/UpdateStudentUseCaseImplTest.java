package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ExistingPaymentException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
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
class UpdateStudentUseCaseImplTest {

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  UpdateStudentUseCaseImpl updateStudentUseCase;

  Conductor mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");
  Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);
  Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício", "mauricio@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.randomUUID(), "Danilo", "Colégio São José", "3° Ano", 140,
          "04", mockConductor, mockResponsible, mockAddress);

  Student mockUpdatedStudent = new Student(UUID.randomUUID(), "Danilo pessoa", "Colégio segura na mao de deus", "69° Ano", 190,
          "09", mockConductor, mockResponsible, mockAddress);

  @Test
  @DisplayName("Nao deve ser possivel atualizar um estudante inexistente")
  void case1() {
    Mockito.when(studentRepository.findById(Mockito.any())).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> {
      updateStudentUseCase.execute(mockStudent.getId(), mockStudent.getName(), mockStudent.getSchool(), mockStudent.getGrade(), mockStudent.getMonthlyPayment(), mockStudent.getMonthlyPaymentExpiration());
    });
  }

  @Test
  @DisplayName("deve ser possivel atualizar um estudante.")
  void case2() {
    Mockito.when(studentRepository.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));

    ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
    Mockito.when(studentRepository.updateStudent(studentCaptor.capture())).thenReturn(mockUpdatedStudent);

    var updatedStudent = updateStudentUseCase.execute(mockStudent.getId(), mockUpdatedStudent.getName(), mockUpdatedStudent.getSchool(), mockUpdatedStudent.getGrade(), mockUpdatedStudent.getMonthlyPayment(), mockUpdatedStudent.getMonthlyPaymentExpiration());

    assertEquals(mockUpdatedStudent.getName(), updatedStudent.getName());
    assertEquals(mockUpdatedStudent.getSchool(), updatedStudent.getSchool());
    assertEquals(mockUpdatedStudent.getGrade(), updatedStudent.getGrade());
    assertEquals(mockUpdatedStudent.getMonthlyPayment(), updatedStudent.getMonthlyPayment());
    assertEquals(mockUpdatedStudent.getMonthlyPaymentExpiration(), updatedStudent.getMonthlyPaymentExpiration());
    assertEquals(mockUpdatedStudent.getConductor(), updatedStudent.getConductor());
    assertEquals(mockUpdatedStudent.getResponsible(), updatedStudent.getResponsible());
    assertEquals(mockUpdatedStudent.getAddress(), updatedStudent.getAddress());
    assertDoesNotThrow(() -> UUID.fromString(updatedStudent.getId().toString()));

    Student studentCapture = studentCaptor.getValue();
    assertEquals(mockUpdatedStudent.getName(), studentCapture.getName());
    assertEquals(mockUpdatedStudent.getSchool(), studentCapture.getSchool());
    assertEquals(mockUpdatedStudent.getGrade(), studentCapture.getGrade());
    assertEquals(mockUpdatedStudent.getMonthlyPayment(), studentCapture.getMonthlyPayment());
    assertEquals(mockUpdatedStudent.getMonthlyPaymentExpiration(), studentCapture.getMonthlyPaymentExpiration());
    assertEquals(mockUpdatedStudent.getConductor(), studentCapture.getConductor());
    assertEquals(mockUpdatedStudent.getResponsible(), studentCapture.getResponsible());
    assertEquals(mockUpdatedStudent.getAddress(), studentCapture.getAddress());
    assertDoesNotThrow(() -> UUID.fromString(studentCapture.getId().toString()));
  }

}