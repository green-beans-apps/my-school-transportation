package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.ConductorProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ConductorNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GetConductorByIdUseCaseImplTest {
    @Mock
    ConductorRepository conductorRepo;

    @InjectMocks
    GetConductorByIdUseCaseImpl getConductorByIdUseCase;

    ConductorProjectionDto mockConductor = new ConductorProjectionDto(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59");

    @Test
    @DisplayName("Deve retornar um condutor")
    void case1() {
        Mockito.when(conductorRepo.findConductorByIdWithoutPassword(mockConductor.getId())).thenReturn(Optional.of(mockConductor));

        var returnConductor = getConductorByIdUseCase.execute(mockConductor.getId());

        assertThat(returnConductor)
                .usingRecursiveComparison()
                .isEqualTo(mockConductor);
    }

    @Test
    @DisplayName("Não deve ser possível retornar um condutor com id inválido")
    void case2() {
        Mockito.when(conductorRepo.findConductorByIdWithoutPassword(mockConductor.getId())).thenReturn(Optional.empty());

        assertThrows(ConductorNotFoundException.class, () -> {
            getConductorByIdUseCase.execute(mockConductor.getId());
        });
    }
}
