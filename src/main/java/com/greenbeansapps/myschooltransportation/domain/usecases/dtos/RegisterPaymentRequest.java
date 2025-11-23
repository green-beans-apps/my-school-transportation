package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record RegisterPaymentRequest(UUID studentId, UUID paymentId, String paymentMonth, Integer paymentYear) {
}
