package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.enums.Shift;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record StudentWithPayments(UUID id, String name, String school, String grade, TransportationType transportationType, Shift shift, Double monthlyPayment, Integer monthlyPaymentExpiration, BigDecimal contractTerminationValue, LocalDate registrationDate, Responsible responsible, Address address, List<Payment> payments) {
}
