package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
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
class UpdateResponsibleStudentUseCaseImplTest {
  @Mock
  private ResponsibleRepository responsibleRepo;

  @Mock
  private StudentRepository studentRepo;

  @InjectMocks
  UpdateResponsibleStudentUseCaseImpl updateResponsibleStudentUseCase;

  Conductor mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
  Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);;
  Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.randomUUID(), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", 140,
          "04", mockConductor, mockResponsible, mockAddress);


  Responsible mockUpdatedResponsible = new Responsible(UUID.randomUUID(), "Maurício O", "mauricio@teste.com", "(81)97310-8001");

  @Test
  @DisplayName("Nao deve atualizar o responsavel de um estudante inexistente")
  void case1() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> {
      updateResponsibleStudentUseCase.execute(UUID.randomUUID(), "john", "jonh@gmail.com",  "819762791782");
    });
  }

  @Test
  @DisplayName("Deve atualizar responsavel com sucesso")
  void case2() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));
    Mockito.when(responsibleRepo.findById(Mockito.any())).thenReturn(Optional.of(mockResponsible));

    ArgumentCaptor<Responsible> responsibleCaptor = ArgumentCaptor.forClass(Responsible.class);
    Mockito.when(responsibleRepo.updateResponsible(responsibleCaptor.capture())).thenReturn(mockResponsible);

    var updatedResponsible = updateResponsibleStudentUseCase.execute(mockStudent.getId(), mockUpdatedResponsible.getName(), mockUpdatedResponsible.getEmail(), mockUpdatedResponsible.getPhoneNumber());


    assertEquals(mockUpdatedResponsible.getName(), updatedResponsible.getName());
    assertEquals(mockUpdatedResponsible.getEmail(), updatedResponsible.getEmail());
    assertEquals(mockUpdatedResponsible.getPhoneNumber(), updatedResponsible.getPhoneNumber());

    Responsible responsibleCapture = responsibleCaptor.getValue();
    assertEquals(mockUpdatedResponsible.getName(), responsibleCapture.getName());
    assertEquals(mockUpdatedResponsible.getEmail(), responsibleCapture.getEmail());
    assertEquals(mockUpdatedResponsible.getPhoneNumber(), responsibleCapture.getPhoneNumber());
  }
}