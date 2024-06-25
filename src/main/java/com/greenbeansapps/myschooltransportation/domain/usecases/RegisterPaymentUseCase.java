package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.RegisterPaymentRequest;

import java.util.UUID;

public interface RegisterPaymentUseCase {
  public Payment execute(RegisterPaymentRequest data);
}
