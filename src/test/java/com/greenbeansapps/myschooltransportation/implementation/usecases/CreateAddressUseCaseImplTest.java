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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CreateAddressUseCaseImplTest {
  @InjectMocks
  CreateAddressUseCaseImpl createAddressUseCase;

  @Mock
  AddressRepository addressRepo;

  @Test
  @DisplayName("Deve cadastrar um novo endereço com sucesso.")
  void case1() {
    ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);

    Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);
    Mockito.when(addressRepo.create(addressCaptor.capture())).thenReturn(mockAddress);

    var newAddress = createAddressUseCase.execute("Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);

    //Checando o retorno do método
    assertEquals(mockAddress.getCity(), newAddress.getCity());
    assertEquals(mockAddress.getDistrict(), newAddress.getDistrict());
    assertEquals(mockAddress.getStreet(), newAddress.getStreet());
    assertEquals(mockAddress.getReferencePoint(), newAddress.getReferencePoint());
    assertEquals(mockAddress.getHouseNumber(), newAddress.getHouseNumber());
    assertDoesNotThrow(() -> UUID.fromString(newAddress.getId().toString()));

    //Checando os dados passados para o address repository
    Address capturedArgument = addressCaptor.getValue();
    assertEquals(mockAddress.getCity(), capturedArgument.getCity());
    assertEquals(mockAddress.getDistrict(), capturedArgument.getDistrict());
    assertEquals(mockAddress.getStreet(), capturedArgument.getStreet());
    assertEquals(mockAddress.getReferencePoint(), capturedArgument.getReferencePoint());
    assertEquals(mockAddress.getHouseNumber(), capturedArgument.getHouseNumber());
    assertDoesNotThrow(() -> UUID.fromString(capturedArgument.getId().toString()));
  }
}