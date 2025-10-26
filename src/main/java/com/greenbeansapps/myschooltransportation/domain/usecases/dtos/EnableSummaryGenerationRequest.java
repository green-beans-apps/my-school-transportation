package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;

public record EnableSummaryGenerationRequest(Months referenceMonth, Integer referenceYear) {
}
