package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.ConductorProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ConductorNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateConductorRequest;
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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UpdateConductorUseCaseImplTest {
    @Mock
    ConductorRepository conductorRepo;

    @InjectMocks
    UpdateConductorUseCaseImpl updateConductorUseCase;

    Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");

    Conductor mockUpdatedConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo Pereira Teste", "danilopereirateste@teste.com", "522.151.300-59", "Davi@280411");

    @Test
    @DisplayName("Deve retornar um condutor")
    void case1() {
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.of(mockConductor));

        ArgumentCaptor<Conductor> conductorCaptor = ArgumentCaptor.forClass(Conductor.class);
        Mockito.when(conductorRepo.updateConductor(conductorCaptor.capture())).thenReturn(mockUpdatedConductor);

        var updatedConductor = updateConductorUseCase.execute(new UpdateConductorRequest(mockUpdatedConductor.getId(), mockUpdatedConductor.getName(), mockUpdatedConductor.getEmail()));

        assertThat(updatedConductor)
                .usingRecursiveComparison()
                .isEqualTo(mockUpdatedConductor);

        Conductor conductorCapture = conductorCaptor.getValue();
        assertThat(conductorCapture)
                .usingRecursiveComparison()
                .isEqualTo(mockUpdatedConductor);
    }

    @Test
    @DisplayName("Não deve ser possível atualizar um estudante inexistente")
    void case2() {
        Mockito.when(conductorRepo.findById(mockConductor.getId())).thenReturn(Optional.empty());

        assertThrows(ConductorNotFoundException.class, () -> {
            updateConductorUseCase.execute(new UpdateConductorRequest(mockUpdatedConductor.getId(), mockUpdatedConductor.getName(), mockUpdatedConductor.getEmail()));
        });
    }
}
