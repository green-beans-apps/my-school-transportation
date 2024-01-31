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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class CreateResponsibleUseCaseImplTest {

    @InjectMocks
    CreateResponsibleUseCaseImpl createResponsibleUseCase;

    @Mock
    ResponsibleRepository responsibleRepo;

    Responsible mockResponsible = new Responsible(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)94545-6666");

    @Test
    @DisplayName("Deve cadastrar um novo responsavel com sucesso.")
    void case1() {
        ArgumentCaptor<Responsible> responsibleCaptor = ArgumentCaptor.forClass(Responsible.class);

        Mockito.when(responsibleRepo.create(responsibleCaptor.capture())).thenReturn(mockResponsible);

        var newResponsible = createResponsibleUseCase.execute(mockResponsible.getId(),mockResponsible.getName(), mockResponsible.getEmail(), mockResponsible.getPhone());

        //chechando retorno do metodo
        assertThat(newResponsible)
                .usingRecursiveComparison()
                .isEqualTo(mockResponsible);

        //Checando os dados passados para o conductor repository
        Responsible responsibleCapture = responsibleCaptor.getValue();
        assertThat(responsibleCapture)
                .usingRecursiveComparison()
                .isEqualTo(mockResponsible);
    }
}
