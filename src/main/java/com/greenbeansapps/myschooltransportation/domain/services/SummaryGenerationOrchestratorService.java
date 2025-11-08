package com.greenbeansapps.myschooltransportation.domain.services;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface SummaryGenerationOrchestratorService {
    CompletableFuture<Void> startAsync(Months month, Integer year, UUID conductorId);
}
