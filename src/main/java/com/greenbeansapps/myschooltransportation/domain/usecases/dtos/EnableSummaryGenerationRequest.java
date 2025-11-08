package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;

import java.util.UUID;

public record EnableSummaryGenerationRequest(Months referenceMonth, Integer referenceYear, UUID conductorId) {
}
