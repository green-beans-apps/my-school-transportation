package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.implementation.protocols.helpers.CryptoHelper;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
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
public class CreateConductorUseCaseImplTest {

    @InjectMocks
    CreateConductorUseCaseImpl createConductorUseCase;

    @Mock
    ConductorRepository conductorRepo;

    @Mock
    CryptoHelper cryptoHelper;

    @Test
    @DisplayName("Deve cadastrar um novo condutor com sucesso.")
    void case1() throws NoSuchAlgorithmException {
        ArgumentCaptor<Conductor> conductorCaptor = ArgumentCaptor.forClass(Conductor.class);

        var mockConductor = new Conductor(UUID.randomUUID(), "Danilo Pereira", "danilopereira@teste.com", "522.151.300-59", "Davi@280411");
        String mockStringHash = "0aa1ea9a5a04b78d4581dd6d17742627";

        Mockito.when(conductorRepo.create(conductorCaptor.capture())).thenReturn(mockConductor);
        Mockito.when(cryptoHelper.generateRash(Mockito.anyString())).thenReturn(mockStringHash);

        var newConductor = createConductorUseCase.execute("Danilo Pereira", "danilopereira@teste.com", "Davi@280411", "522.151.300-59");

        assertEquals(mockConductor.getName(), newConductor.getName());
        assertEquals(mockConductor.getEmail(), newConductor.getEmail());
        assertEquals(mockStringHash, newConductor.getPassword());
        assertEquals(mockConductor.getCpf(), newConductor.getCpf());
        assertDoesNotThrow(() -> UUID.fromString(newConductor.getId().toString()));

        //Checando os dados passados para o conductor repository
        Conductor conductorCapture = conductorCaptor.getValue();
        assertEquals(mockConductor.getName(), conductorCapture.getName());
        assertEquals(mockConductor.getEmail(), conductorCapture.getEmail());
        assertEquals(mockStringHash, conductorCapture.getPassword());
        assertEquals(mockConductor.getCpf(), conductorCapture.getCpf());
        assertDoesNotThrow(() -> UUID.fromString(conductorCapture.getId().toString()));
    }
}
