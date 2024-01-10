package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.*;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exeptions.ExistingPaymentException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.InvalidAddressException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.StudentNotFoundException;
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

  Conductor mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");
  Address mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "São José", 123);
  Responsible mockResponsible = new Responsible(UUID.randomUUID(), "Maurício", "mauricio@teste.com", "(81)97314-8001");
  Student mockStudent = new Student(UUID.randomUUID(), "Danilo", "Colégio São José", "3° Ano", 140,
          "04", mockConductor, mockResponsible, mockAddress);
  Payment mockPayment = new Payment(UUID.randomUUID(), new Date(), Months.JANEIRO, mockStudent );

  @Test
  @DisplayName("Nao deve ser possivel registrar pagamento de um estudante invalido")
  void case1() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.empty());

    assertThrows(StudentNotFoundException.class, () -> {
      registerPaymentUseCase.execute(mockStudent.getId(), mockPayment.getPaymentMonth());
    });
  }

  @Test
  @DisplayName("Nao deve ser possivel registrar pagamento duas vezes em um mesmo mes.")
  void case2() {
    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));
    Mockito.when(paymentRepo.findPaymentPerMonth(mockStudent.getId(),mockPayment.getPaymentMonth())).thenReturn(Optional.of(mockPayment));

    assertThrows(ExistingPaymentException.class, () -> {
      registerPaymentUseCase.execute(mockStudent.getId(), mockPayment.getPaymentMonth());
    });
  }

  @Test
  @DisplayName("Deve ser possível registrar um pagamento válido.")
  void case3() {
    ArgumentCaptor<Payment> paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);

    Mockito.when(studentRepo.findById(Mockito.any())).thenReturn(Optional.of(mockStudent));
    Mockito.when(paymentRepo.findPaymentPerMonth(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
    Mockito.when(paymentRepo.register(paymentArgumentCaptor.capture())).thenReturn(mockPayment);

    Payment paymentReturn = registerPaymentUseCase.execute(mockStudent.getId(), mockPayment.getPaymentMonth());

    // comparando retorno
    assertEquals(mockPayment.getPaymentMonth(), paymentReturn.getPaymentMonth());
    assertEquals(
            LocalDate.ofInstant(mockPayment.getPaymentDate().toInstant(), ZoneId.systemDefault()),
            LocalDate.ofInstant(paymentReturn.getPaymentDate().toInstant(), ZoneId.systemDefault())
    );
    assertEquals(mockPayment.getPaymentDate().toInstant().atZone(ZoneId.systemDefault()).getHour(), paymentReturn.getPaymentDate().toInstant().atZone(ZoneId.systemDefault()).getHour());
    assertEquals(mockPayment.getPaymentDate().toInstant().atZone(ZoneId.systemDefault()).getMinute(), paymentReturn.getPaymentDate().toInstant().atZone(ZoneId.systemDefault()).getMinute());
    assertEquals(mockPayment.getStudent(), paymentReturn.getStudent());

    // comparando dados passados para o repositorio
    Payment paymentArgumentCaptured = paymentArgumentCaptor.getValue();
    assertEquals(mockPayment.getPaymentMonth(), paymentArgumentCaptured.getPaymentMonth());
    assertEquals(
            LocalDate.ofInstant(mockPayment.getPaymentDate().toInstant(), ZoneId.systemDefault()),
            LocalDate.ofInstant(paymentArgumentCaptured.getPaymentDate().toInstant(), ZoneId.systemDefault())
    );
    assertEquals(mockPayment.getPaymentDate().toInstant().atZone(ZoneId.systemDefault()).getHour(), paymentArgumentCaptured.getPaymentDate().toInstant().atZone(ZoneId.systemDefault()).getHour());
    assertEquals(mockPayment.getPaymentDate().toInstant().atZone(ZoneId.systemDefault()).getMinute(), paymentArgumentCaptured.getPaymentDate().toInstant().atZone(ZoneId.systemDefault()).getMinute());
    assertEquals(mockPayment.getStudent(), paymentArgumentCaptured.getStudent());

  }

}