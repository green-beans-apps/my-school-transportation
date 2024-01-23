package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
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
class DeleteStudentUseCaseImplTest {

  @Mock
  private StudentRepository studentRepo;
  @Mock
  private AddressRepository addressRepo;
  @Mock
  private ResponsibleRepository responsibleRepo;

  @InjectMocks
  DeleteStudentUseCaseImpl deleteStudentUseCase;

  Conductor mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");
  Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);
  Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício", "mauricio@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.randomUUID(), "Danilo", "Colégio São José", "3° Ano", 140,
          "04", mockConductor, mockResponsible, mockAddress);


  @Test
  @DisplayName("Nao deve ser possivel deletar um estudante inexistente")
  void case1() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> {
      deleteStudentUseCase.execute(mockStudent.getId());
    });
  }
  @Test
  @DisplayName("Deve ser possível deletar um estudante existente")
  void case2() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));
    Mockito.when(addressRepo.deleteAddress(Mockito.any())).thenReturn(true);
    Mockito.when(responsibleRepo.deleteResponsible(Mockito.any())).thenReturn(true);

    boolean result = deleteStudentUseCase.execute(mockStudent.getId());

    Mockito.verify(studentRepo).deleteStudent(mockStudent.getId());
    Mockito.verify(addressRepo).deleteAddress(mockAddress.getId());
    Mockito.verify(responsibleRepo).deleteResponsible(mockResponsible.getId());
    assertTrue(result);
  }

}


