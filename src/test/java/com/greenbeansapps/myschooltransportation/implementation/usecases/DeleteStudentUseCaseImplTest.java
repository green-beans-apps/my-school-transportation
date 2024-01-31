package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
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

  Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
  Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);;
  Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA.toString(), 140,
          "04", "manha", mockConductor, mockResponsible, mockAddress);


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


