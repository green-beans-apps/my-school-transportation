package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateAddressUseCaseImplTest {

   @InjectMocks
   CreateAddressUseCaseImpl createAddressUseCase;

   @Mock
   AddressRepository addressRepo;

  @Test
  @DisplayName("Deve cadastrar um novo endereco com sucesso.")
  void case1() {
    ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);

    var mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "Rua sao jose", 123);
    Mockito.when(addressRepo.create(addressCaptor.capture())).thenReturn(mockAddress);

    Address newAddress = createAddressUseCase.execute("Olinda", "Pernambuco", "Rua sao jose", 123);

    Mockito.verify(addressRepo).create(Mockito.any(Address.class));

    // checando o retorno do metodo
    assertEquals("Olinda", newAddress.getCity());
    assertEquals("Pernambuco", newAddress.getDistrict());
    assertEquals("Rua sao jose", newAddress.getStreet());
    assertEquals(123, newAddress.getHouseNumber());
    Assertions.assertDoesNotThrow(() -> UUID.fromString(newAddress.getId().toString()));

    //checando os dados passados para o address repository
    Address capturedArgument = addressCaptor.getValue();
    assertEquals("Olinda", capturedArgument.getCity());
    assertEquals("Pernambuco", capturedArgument.getDistrict());
    assertEquals("Rua sao jose", capturedArgument.getStreet());
    assertEquals(123, capturedArgument.getHouseNumber());
    Assertions.assertDoesNotThrow(() -> UUID.fromString(capturedArgument.getId().toString()));
  }
}