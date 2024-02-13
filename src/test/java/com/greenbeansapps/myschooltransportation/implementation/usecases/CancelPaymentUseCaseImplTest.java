package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.*;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.enums.Shift;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.PaymentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CancelPaymentUseCaseImplTest {

    @Mock
    PaymentRepository paymentRepo;

    @InjectMocks
    CancelPaymentUseCaseImpl cancelPaymentUseCase;

    Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
    Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", 123);;
    Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
    Student mockStudent = new Student(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA.toString(), 140,
            4, "manha", mockConductor, mockResponsible, mockAddress);

    String pattern = "dd/MM/yyyy";
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String formattedDate = currentDate.format(formatter);
    Payment mockPayment = new Payment(UUID.fromString("2fdf83ab-c4d4-4a05-981e-81d566ccd168"), formattedDate, Months.JANEIRO, mockStudent);

    @Test
    @DisplayName("Nao deve ser possivel cancelar um pagamento inexistente")
    void case1() {
        Mockito.when(paymentRepo.findPayment(mockPayment.getId())).thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class, () -> {
            cancelPaymentUseCase.execute(mockPayment.getId());
        });
    }

    @Test
    @DisplayName("Deve ser possível cancelar um pagamento efetuado.")
    void case2() {
        Mockito.when(paymentRepo.findPayment(mockPayment.getId())).thenReturn(Optional.of(mockPayment));

        boolean result = cancelPaymentUseCase.execute(mockPayment.getId());

        Mockito.verify(paymentRepo).cancelPayment(mockPayment.getId());
        assertTrue(result);
    }
}
