package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.*;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ExistingPaymentException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidMonthException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegisterPaymentUseCaseImplTest {

  @Mock
  private  PaymentRepository paymentRepo;
  @Mock
  private StudentRepository studentRepo;


  @InjectMocks
  RegisterPaymentUseCaseImpl registerPaymentUseCase;

  Conductor mockConductor = new Conductor(UUID.fromString("c487b1aa-e239-4869-82d4-c38f33dd9ba2"), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;;
  Address mockAddress = new Address(UUID.fromString( "99b7d061-1ad2-46de-aad5-9da1376fb572"),"Olinda", "Pernambuco", "Rua São José", "Próximo ao mercado X", "123");;
  Responsible mockResponsible = new Responsible(UUID.fromString("c43b3422-f72a-4c1f-9b99-59b3261e5e3d"), "Maurício Ferraz", "mauricioferraz@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.fromString("28305d91-9d9f-4311-b2ec-f6a12f1bcd4e"), "Danilo Pereira Pessoa", "Colégio de São José", "3° Ano (Médio)", TransportationType.IDA_E_VOLTA.toString(), 140.90,
          4, "manha", mockConductor, mockResponsible, mockAddress);

  String pattern = "dd/MM/yyyy";
  LocalDate currentDate = LocalDate.now();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
  String formattedDate = currentDate.format(formatter);

  Payment mockPayment = new Payment(UUID.fromString("2fdf83ab-c4d4-4a05-981e-81d566ccd168"), formattedDate, Months.JANEIRO, mockStudent );

  @Test
  @DisplayName("Nao deve ser possivel registrar pagamento de um estudante invalido")
  void case1() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> {
      registerPaymentUseCase.execute(UUID.randomUUID(), mockStudent.getId(), "Janeiro");
    });
  }

  @Test
  @DisplayName("Nao deve ser possivel registrar pagamento duas vezes em um mesmo mes.")
  void case2() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));
    Mockito.when(paymentRepo.findPaymentPerMonth(mockStudent.getId(),mockPayment.getPaymentMonth())).thenReturn(Optional.of(mockPayment));

    assertThrows(ExistingPaymentException.class, () -> {
      registerPaymentUseCase.execute(UUID.randomUUID(), mockStudent.getId(), "Janeiro");
    });
  }

  @Test
  @DisplayName("Nao deve ser possivel registrar um pagamento com o mes invalido")
  void case3() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));

    assertThrows(InvalidMonthException.class, () -> {
      registerPaymentUseCase.execute(UUID.randomUUID(), mockStudent.getId(), "Janeir");
    });
  }

  @Test
  @DisplayName("Deve ser possível registrar um pagamento válido.")
  void case4() {
    ArgumentCaptor<Payment> paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);

    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));
    Mockito.when(paymentRepo.findPaymentPerMonth(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
    Mockito.when(paymentRepo.register(paymentArgumentCaptor.capture())).thenReturn(mockPayment);

    Payment paymentReturn = registerPaymentUseCase.execute(UUID.randomUUID(), mockStudent.getId(), "Janeiro");

    // comparando retorno
    assertEquals(mockPayment.getPaymentMonth(), paymentReturn.getPaymentMonth());

    assertEquals(
            mockPayment.getPaymentDate(),
            paymentReturn.getPaymentDate()
    );
    assertEquals(mockPayment.getStudent(), paymentReturn.getStudent());

    // comparando dados passados para o repositorio
    Payment paymentArgumentCaptured = paymentArgumentCaptor.getValue();
    assertEquals(mockPayment.getPaymentMonth(), paymentArgumentCaptured.getPaymentMonth());
    assertEquals(
            mockPayment.getPaymentDate(),
            paymentArgumentCaptured.getPaymentDate()
    );
    assertEquals(mockPayment.getStudent(), paymentArgumentCaptured.getStudent());
  }

}