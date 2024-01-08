package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class CreateAddressUseCaseImplTest {
  @Test
  @DisplayName("Criar novo endereço")
  void testExecute() {
    // Criação do mock a ser testado
    AddressRepository addressRepoMock = Mockito.mock(AddressRepository.class);

    //Passando para classe de implementação o mock
    CreateAddressUseCaseImpl createAddressUseCase = new CreateAddressUseCaseImpl(addressRepoMock);

    //Chamando o método que será testado
    createAddressUseCase.execute("Recife", "Iputinga", "Rua José Trigueiro", 46);

    //Servirá para assegurar que o método só foi chamado 1 vez com qualquer instância de Address como argumento
    Mockito.verify(addressRepoMock, Mockito.times(1)).create(Mockito.any(Address.class));

    //Irá capturar os argumento passados para o método create. Permitem ser acessados depois
    ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
    Mockito.verify(addressRepoMock).create(addressCaptor.capture());

    //Verificação dos parâmetros
    Address addressCapture = addressCaptor.getValue();
    Assertions.assertEquals("Recife", addressCapture.getCity());
    Assertions.assertEquals("Iputinga", addressCapture.getDistrict());
    Assertions.assertEquals("Rua José Trigueiro", addressCapture.getStreet());
    Assertions.assertEquals(46, addressCapture.getHouseNumber());
  }
}