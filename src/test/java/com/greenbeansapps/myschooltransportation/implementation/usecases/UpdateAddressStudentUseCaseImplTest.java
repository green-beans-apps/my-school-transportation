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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateAddressStudentUseCaseImplTest {

  @Mock
  private AddressRepository addressRepo;
  @Mock
  private StudentRepository studentRepo;

  @InjectMocks
  UpdateAddressStudentUseCaseImpl updateAddressStudentUseCase;

  Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
  Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", "");;
  Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA.toString(), 140.90,
          4, "manha", mockConductor, mockResponsible, mockAddress);
  Address mockUpdatedAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", "123");

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

    assertThat(updatedAddress)
            .usingRecursiveComparison()
            .isEqualTo(mockUpdatedAddress);

    Address addressCapture = addressCaptor.getValue();
    assertThat(addressCapture)
            .usingRecursiveComparison()
            .isEqualTo(mockUpdatedAddress);
  }
}
