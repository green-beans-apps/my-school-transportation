package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record RegisterPaymentRequest(UUID paymentId, UUID studentId, String paymentMonth, Integer paymentYear) {
}
