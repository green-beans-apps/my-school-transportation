package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.implementation.protocols.helpers.CryptoHelper;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class CreateResponsibleUseCaseImplTest {

    @InjectMocks
    CreateResponsibleUseCaseImpl createResponsibleUseCase;

    @Mock
    ResponsibleRepository responsibleRepo;

    @Test
    @DisplayName("Deve cadastrar um novo responsavel com sucesso.")
    void case1() {
        ArgumentCaptor<Responsible> responsibleCaptor = ArgumentCaptor.forClass(Responsible.class);

        var mockResponsible = new Responsible(UUID.randomUUID(), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)94545-6666");
        Mockito.when(responsibleRepo.create(responsibleCaptor.capture())).thenReturn(mockResponsible);

        var newResponsible = createResponsibleUseCase.execute("Maurício Ferraz", "mauricioferraz@teste.com", "(81)94545-6666");

        //chechando retorno do metodo
        assertEquals(mockResponsible.getName(), newResponsible.getName());
        assertEquals(mockResponsible.getEmail(), newResponsible.getEmail());
        assertEquals(mockResponsible.getPhoneNumber(), newResponsible.getPhoneNumber());
        assertDoesNotThrow(() -> UUID.fromString( newResponsible.getId().toString()));

        //Checando os dados passados para o conductor repository
        Responsible responsibleCapture = responsibleCaptor.getValue();
        assertEquals(mockResponsible.getName(), responsibleCapture.getName());
        assertEquals(mockResponsible.getEmail(), responsibleCapture.getEmail());
        assertEquals(mockResponsible.getPhoneNumber(), responsibleCapture.getPhoneNumber());
        assertDoesNotThrow(() -> UUID.fromString(responsibleCapture.getId().toString()));
    }
}
