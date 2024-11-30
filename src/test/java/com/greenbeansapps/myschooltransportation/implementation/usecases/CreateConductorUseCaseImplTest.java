package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.exceptions.EmailAlreadyRegisteredException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.PasswordIsNotValidException;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateConductorRequest;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CreateConductorUseCaseImplTest {

    @InjectMocks
    CreateConductorUseCaseImpl createConductorUseCase;

    @Mock
    ConductorRepository conductorRepo;

    @Mock
    CryptoHelper cryptoHelper;

    Conductor mockConductor = new Conductor(UUID.fromString("69dd2075-3d7b-443c-a5b6-c68441d479e1"), "danilao",  "danilao@gmail.com", "52187952088", "123@mudar.");
    @Test
    @DisplayName("Deve cadastrar um novo condutor com sucesso.")
    void case1() throws NoSuchAlgorithmException {
        ArgumentCaptor<Conductor> conductorCaptor = ArgumentCaptor.forClass(Conductor.class);

        Mockito.when(conductorRepo.create(conductorCaptor.capture())).thenReturn(mockConductor);
        Mockito.when(cryptoHelper.generateRash(Mockito.anyString())).thenReturn(mockConductor.getPassword());

        var newConductor = createConductorUseCase.execute(new CreateConductorRequest(mockConductor.getId(),mockConductor.getName(), mockConductor.getEmail(), mockConductor.getPassword(), mockConductor.getCpf()));

        assertThat(newConductor)
                .usingRecursiveComparison()
                .isEqualTo(mockConductor);

        Conductor conductorCapture = conductorCaptor.getValue();
        assertThat(conductorCapture)
                .usingRecursiveComparison()
                .isEqualTo(mockConductor);

    }

    @Test
    @DisplayName("Não deve cadastrar um condutor com um e-mail já registrado")
    void case2() {
        Mockito.when(conductorRepo.findByEmail(mockConductor.getEmail()))
                .thenReturn(Optional.of(mockConductor));

        CreateConductorRequest request = new CreateConductorRequest(
                mockConductor.getId(),
               mockConductor.getUsername(),
                mockConductor.getEmail(), // E-mail já existente
                mockConductor.getPassword(),
                mockConductor.getCpf()
        );

        assertThrows(
                EmailAlreadyRegisteredException.class,
                () -> createConductorUseCase.execute(request)
        );

        Mockito.verify(conductorRepo, Mockito.never()).create(Mockito.any(Conductor.class));
    }

    @Test
    @DisplayName("Não deve cadastrar um condutor com a senha inferior a 6 caracteres")
    void case3() {
        // Senha não válida
        mockConductor.setPassword("se123");

        CreateConductorRequest request = new CreateConductorRequest(
                mockConductor.getId(),
                mockConductor.getUsername(),
                mockConductor.getEmail(),
                mockConductor.getPassword(),
                mockConductor.getCpf()
        );

        assertThrows(
                PasswordIsNotValidException.class,
                () -> createConductorUseCase.execute(request)
        );

        Mockito.verify(conductorRepo, Mockito.never()).create(Mockito.any(Conductor.class));
    }

}
