package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CreateAddressUseCaseImplTest {
  @InjectMocks
  CreateAddressUseCaseImpl createAddressUseCase;

  @Mock
  AddressRepository addressRepo;

  Address mockAddress = new Address(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);

  @Test
  @DisplayName("Deve cadastrar um novo endereço com sucesso.")
  void case1() {
    ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
    Mockito.when(addressRepo.create(addressCaptor.capture())).thenReturn(mockAddress);

    var newAddress = createAddressUseCase.execute(mockAddress.getId(),mockAddress.getCity(), mockAddress.getDistrict(), mockAddress.getStreet(), mockAddress.getReferencePoint(), mockAddress.getHouseNumber());

    //Checando o retorno do método
    assertThat(newAddress)
            .usingRecursiveComparison()
            .isEqualTo(mockAddress);

    //Checando os dados passados para o address repository
    Address capturedArgument = addressCaptor.getValue();
    assertThat(capturedArgument)
            .usingRecursiveComparison()
            .isEqualTo(mockAddress);
  }
}