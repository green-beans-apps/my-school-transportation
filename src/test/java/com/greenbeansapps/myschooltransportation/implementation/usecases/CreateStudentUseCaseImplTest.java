package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.implementation.protocols.helpers.CryptoHelper;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import com.greenbeansapps.myschooltransportation.infra.helpers.CryptoHelperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class CreateStudentUseCaseImplTest {
    @Test
    @DisplayName("Criar novo estudante")
    void testExecute() throws NoSuchAlgorithmException {
        StudentRepository studentRepoMock = Mockito.mock(StudentRepository.class);
        ResponsibleRepository responsibleRepoMock = Mockito.mock(ResponsibleRepository.class);
        AddressRepository addressRepoMock = Mockito.mock(AddressRepository.class);
        ConductorRepository conductorRepoMock = Mockito.mock(ConductorRepository.class);

        //Criando ambiente de teste para responsável
        CreateResponsibleUseCaseImpl createResponsibleUseCase = new CreateResponsibleUseCaseImpl(responsibleRepoMock);

        createResponsibleUseCase.execute("Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");

        Mockito.verify(responsibleRepoMock, Mockito.times(1)).create(Mockito.any(Responsible.class));

        ArgumentCaptor<Responsible> responsibleCaptor = ArgumentCaptor.forClass(Responsible.class);
        Mockito.verify(responsibleRepoMock).create(responsibleCaptor.capture());

        Responsible responsibleCapture = responsibleCaptor.getValue();

        //Criando ambiente de teste para endereço
        CreateAddressUseCaseImpl createAddressUseCase = new CreateAddressUseCaseImpl(addressRepoMock);

        createAddressUseCase.execute("Recife", "Iputinga", "Rua José Trigueiro", 46);

        Mockito.verify(addressRepoMock, Mockito.times(1)).create(Mockito.any(Address.class));

        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
        Mockito.verify(addressRepoMock).create(addressCaptor.capture());

        Address addressCapture = addressCaptor.getValue();

        //Criando ambiente de teste para condutor
        CryptoHelper cryptoHelper = new CryptoHelperImpl();

        CreateConductorUseCaseImpl createConductorUseCase = new CreateConductorUseCaseImpl(conductorRepoMock, cryptoHelper);
        createConductorUseCase.execute("Danilo Pereira", "danilopereira@teste.com", "Davi@280411", "709.269.174-55");

        Mockito.verify(conductorRepoMock, Mockito.times(1)).create(Mockito.any(Conductor.class));

        ArgumentCaptor<Conductor> conductorCaptor = ArgumentCaptor.forClass(Conductor.class);
        Mockito.verify(conductorRepoMock).create(conductorCaptor.capture());

        Conductor conductorCapture = conductorCaptor.getValue();

        //Criando ambiente de teste para estudante

        //Definindo o retorno dos métodos de estudante para verificar se condutor, responsável e endereço existem
        Mockito.when(conductorRepoMock.findById(conductorCapture.getId())).thenReturn(Optional.of(conductorCapture));
        Mockito.when(responsibleRepoMock.findById(responsibleCapture.getId())).thenReturn(Optional.of(responsibleCapture));
        Mockito.when(addressRepoMock.findById(addressCapture.getId())).thenReturn(Optional.of(addressCapture));

        CreateStudentUseCaseImpl createStudentUseCase = new CreateStudentUseCaseImpl(studentRepoMock, responsibleRepoMock, addressRepoMock, conductorRepoMock);

        createStudentUseCase.execute("Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", 140,
                "04", conductorCapture.getId(), responsibleCapture.getId(), addressCapture.getId());

        Mockito.verify(studentRepoMock, Mockito.times(1)).create(Mockito.any(Student.class));

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepoMock).create(studentCaptor.capture());

        Student studentCapture = studentCaptor.getValue();

        //Testes
        Assertions.assertEquals("Danilo Pereira Pessoa", studentCapture.getName());
        Assertions.assertEquals("Colégio de São José", studentCapture.getSchool());
        Assertions.assertEquals("3° Ano (Médio)", studentCapture.getGrade());
        Assertions.assertEquals(140, studentCapture.getMonthlyPayment());
        Assertions.assertEquals("04", studentCapture.getMonthlyPaymentExpiration());
        Assertions.assertEquals(conductorCapture, studentCapture.getConductor());
        Assertions.assertEquals(responsibleCapture, studentCapture.getResponsible());
        Assertions.assertEquals(addressCapture, studentCapture.getAddress());
    }
}
