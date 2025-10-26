package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record RegisterPaymentRequest(UUID studentId, String paymentMonth, Integer paymentYear, BigDecimal paymentAmount, UUID monthlyFeeId) {
}
