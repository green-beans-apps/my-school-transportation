package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.*;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exeptions.ExistingPaymentException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.InvalidAddressException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

  Conductor mockConductor;
  Address mockAddress;
  Responsible mockResponsible;
  Student mockStudent;
  Payment mockPayment;

  @BeforeEach
  void setup() {
    mockConductor = new Conductor(UUID.randomUUID(), "Danilo P", "danilo@teste.com", "522.151.300-59", "Davi@280411");;
    mockAddress = new Address(UUID.randomUUID(),"Olinda", "Pernambuco", "São José", 123);
    mockResponsible = new Responsible(UUID.randomUUID(), "Maurício", "mauricio@teste.com", "(81)97314-8001");
    mockStudent = new Student(UUID.randomUUID(), "Danilo", "Colégio São José", "3° Ano", 140,
            "04", mockConductor, mockResponsible, mockAddress);

    this.mockPayment = new Payment(UUID.randomUUID(), new Date(), Months.JANEIRO, mockStudent );
  }

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
    Mockito.when(paymentRepo.findPaymentPerMonth(Mockito.any(), Mockito.any())).thenReturn(Optional.of(mockPayment));

    assertThrows(ExistingPaymentException.class, () -> {
      registerPaymentUseCase.execute(mockStudent.getId(), mockPayment.getPaymentMonth());
    });
  }

}