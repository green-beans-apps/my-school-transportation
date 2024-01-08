package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class CreateResponsibleUseCaseImplTest {
    @Test
    @DisplayName("Criar novo responsável")
    void testExecute() {
        ResponsibleRepository responsibleRepoMock = Mockito.mock(ResponsibleRepository.class);

        CreateResponsibleUseCaseImpl createResponsibleUseCase = new CreateResponsibleUseCaseImpl(responsibleRepoMock);

        createResponsibleUseCase.execute("Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");

        Mockito.verify(responsibleRepoMock, Mockito.times(1)).create(Mockito.any(Responsible.class));

        ArgumentCaptor<Responsible> responsibleCaptor = ArgumentCaptor.forClass(Responsible.class);
        Mockito.verify(responsibleRepoMock).create(responsibleCaptor.capture());

        Responsible responsibleCapture = responsibleCaptor.getValue();
        Assertions.assertEquals("Maurício Ferraz", responsibleCapture.getName());
        Assertions.assertEquals("mauricioferraz@teste.com", responsibleCapture.getEmail());
        Assertions.assertEquals("(81)97314-8001", responsibleCapture.getPhoneNumber());
    }
}
