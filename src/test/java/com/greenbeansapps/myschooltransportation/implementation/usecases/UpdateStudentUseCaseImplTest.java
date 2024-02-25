package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ExistingPaymentException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidMonthlyPaymentExpirationException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateStudentRequest;
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
class UpdateStudentUseCaseImplTest {

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  UpdateStudentUseCaseImpl updateStudentUseCase;

  Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
  Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", "123");;
  Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA.toString(), 140.90,
          4, "manha", mockConductor, mockResponsible, mockAddress);

  Student mockUpdatedStudent = new Student(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo pessoa", "Colégio segura na mao de deus", "6° Ano", TransportationType.IDA_E_VOLTA.toString(), 190.90,
          18, "manha", mockConductor, mockResponsible, mockAddress);

  @Test
  @DisplayName("Nao deve ser possivel atualizar um estudante inexistente")
  void case1() {
    Mockito.when(studentRepository.findById(Mockito.any())).thenReturn(Optional.empty());
    assertThrows(StudentNotFoundException.class, () -> {
      updateStudentUseCase.execute(new UpdateStudentRequest(mockUpdatedStudent.getId(), mockUpdatedStudent.getName(), mockUpdatedStudent.getSchool(), mockUpdatedStudent.getGrade(), mockUpdatedStudent.getTransportationType().toString(), mockUpdatedStudent.getMonthlyPayment(), mockUpdatedStudent.getMonthlyPaymentExpiration(), mockUpdatedStudent.getShift().toString()));
    });
  }

  @Test
  @DisplayName("Nao deve ser possivel atualizar um estudante com uma data que nao esteja entre 1 e 28")
  void case2() {
    Mockito.when(studentRepository.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));

    assertThrows(InvalidMonthlyPaymentExpirationException.class, () -> {
      updateStudentUseCase.execute(new UpdateStudentRequest(mockUpdatedStudent.getId(), mockUpdatedStudent.getName(), mockUpdatedStudent.getSchool(), mockUpdatedStudent.getGrade(), mockUpdatedStudent.getTransportationType().toString(), mockUpdatedStudent.getMonthlyPayment(), 29, mockUpdatedStudent.getShift().toString()));
    });
  }

  @Test
  @DisplayName("deve ser possivel atualizar um estudante.")
  void case3() {
    Mockito.when(studentRepository.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));

    ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
    Mockito.when(studentRepository.updateStudent(studentCaptor.capture())).thenReturn(mockUpdatedStudent);

    var updatedStudent = updateStudentUseCase.execute(new UpdateStudentRequest(mockUpdatedStudent.getId(), mockUpdatedStudent.getName(), mockUpdatedStudent.getSchool(), mockUpdatedStudent.getGrade(), mockUpdatedStudent.getTransportationType().toString(), mockUpdatedStudent.getMonthlyPayment(), mockUpdatedStudent.getMonthlyPaymentExpiration(), mockUpdatedStudent.getShift().toString()));

    assertThat(updatedStudent)
            .usingRecursiveComparison()
            .isEqualTo(mockUpdatedStudent);

    Student studentCapture = studentCaptor.getValue();
    assertThat(studentCapture)
            .usingRecursiveComparison()
            .isEqualTo(mockUpdatedStudent);
  }

}