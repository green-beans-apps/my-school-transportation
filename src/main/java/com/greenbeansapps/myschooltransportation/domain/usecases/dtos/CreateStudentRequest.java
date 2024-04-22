package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record CreateStudentRequest(UUID id, String name, String school, String grade, String transportationType, Double monthlyPayment, Integer monthlyPaymentExpiration, String shift, UUID conductorId, UUID responsibleId, UUID addressId) {
}
