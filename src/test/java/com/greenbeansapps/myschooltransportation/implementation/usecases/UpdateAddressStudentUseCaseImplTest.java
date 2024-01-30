package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
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
class UpdateAddressStudentUseCaseImplTest {

  @Mock
  private AddressRepository addressRepo;
  @Mock
  private StudentRepository studentRepo;

  @InjectMocks
  UpdateAddressStudentUseCaseImpl updateAddressStudentUseCase;

  Conductor mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");
  Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);
  Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício", "mauricio@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.randomUUID(), "Danilo", "Colégio São José", "3° Ano", TransportationType.IDA_E_VOLTA, 140,
          "04", mockConductor, mockResponsible, mockAddress);
  Address mockUpdatedAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);

  @Test
  @DisplayName("Nao deve ser possivel atualizar um endereco vinculado a um estudante inexistente")
  void case1() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> {
      updateAddressStudentUseCase.execute(mockStudent.getId(), mockUpdatedAddress.getCity(), mockUpdatedAddress.getDistrict(), mockUpdatedAddress.getStreet(), mockUpdatedAddress.getReferencePoint(), mockUpdatedAddress.getHouseNumber());
    });
  }

  @Test
  @DisplayName("Deve ser possivel atualizar um endereco vinculado a um estudante")
  void case2() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));
    Mockito.when(addressRepo.findById(Mockito.any())).thenReturn(Optional.of(mockAddress));

    ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
    Mockito.when(addressRepo.updateAddress(addressCaptor.capture())).thenReturn(mockUpdatedAddress);

    var updatedAddress = updateAddressStudentUseCase.execute(mockStudent.getId(), mockUpdatedAddress.getCity(), mockUpdatedAddress.getDistrict(), mockUpdatedAddress.getStreet(), mockUpdatedAddress.getReferencePoint(), mockUpdatedAddress.getHouseNumber());

    assertEquals(mockUpdatedAddress.getCity(), updatedAddress.getCity());
    assertEquals(mockUpdatedAddress.getDistrict(), updatedAddress.getDistrict());
    assertEquals(mockUpdatedAddress.getStreet(), updatedAddress.getStreet());
    assertEquals(mockUpdatedAddress.getReferencePoint(), updatedAddress.getReferencePoint());
    assertEquals(mockUpdatedAddress.getHouseNumber(), updatedAddress.getHouseNumber());

    Address addressCapture = addressCaptor.getValue();
    assertEquals(mockUpdatedAddress.getCity(), addressCapture.getCity());
    assertEquals(mockUpdatedAddress.getDistrict(), addressCapture.getDistrict());
    assertEquals(mockUpdatedAddress.getStreet(), addressCapture.getStreet());
    assertEquals(mockUpdatedAddress.getReferencePoint(), addressCapture.getReferencePoint());
    assertEquals(mockUpdatedAddress.getHouseNumber(), addressCapture.getHouseNumber());
  }
}
