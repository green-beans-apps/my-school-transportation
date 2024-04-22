package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record UpdateStudentRequest(UUID StudentId, String name, String school, String grade, String transportationType, Double monthlyPayment, Integer monthlyPaymentExpiration, String shift) {
}
