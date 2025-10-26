package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;

import java.math.BigDecimal;
import java.util.UUID;

public record RegisterMonthlyFeeRequest(UUID studentId, Months referenceMonth, String referenceYear, BigDecimal amount) {
}
