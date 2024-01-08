package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.implementation.protocols.helpers.CryptoHelper;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.infra.helpers.CryptoHelperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.security.NoSuchAlgorithmException;

public class CreateConductorUseCaseImplTest {
    @Test
    @DisplayName("Criar novo condutor")
    void testExecute() throws NoSuchAlgorithmException {
        ConductorRepository conductorRepoMock = Mockito.mock(ConductorRepository.class);
        CryptoHelper cryptoHelper = new CryptoHelperImpl();

        CreateConductorUseCaseImpl createConductorUseCase = new CreateConductorUseCaseImpl(conductorRepoMock, cryptoHelper);
        createConductorUseCase.execute("Danilo Pereira", "danilopereira@teste.com", "Davi@280411", "709.269.174-55");

        Mockito.verify(conductorRepoMock, Mockito.times(1)).create(Mockito.any(Conductor.class));

        ArgumentCaptor<Conductor> conductorCaptor = ArgumentCaptor.forClass(Conductor.class);
        Mockito.verify(conductorRepoMock).create(conductorCaptor.capture());

        Conductor conductorCapture = conductorCaptor.getValue();
        Assertions.assertEquals("Danilo Pereira", conductorCapture.getName());
        Assertions.assertEquals("danilopereira@teste.com", conductorCapture.getEmail());
        Assertions.assertEquals(cryptoHelper.generateRash("Davi@280411"), conductorCapture.getPassword());
        Assertions.assertEquals("709.269.174-55", conductorCapture.getCpf());
    }
}
