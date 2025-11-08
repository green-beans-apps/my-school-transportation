package com.greenbeansapps.myschooltransportation.implementation.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.services.SummaryGenerationOrchestratorService;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.EnableSummaryGenerationRequest;
import com.greenbeansapps.myschooltransportation.implementation.usecases.CalculateContractTerminationUseCaseImpl;
import com.greenbeansapps.myschooltransportation.implementation.usecases.EnableSummaryGenerationUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class SummaryGenerationOrchestratorServiceImpl implements SummaryGenerationOrchestratorService {

    @Autowired
    private EnableSummaryGenerationUseCaseImpl summaryStep1;

    @Autowired
    private CalculateContractTerminationUseCaseImpl summaryStep2;

    @Async
    public CompletableFuture<Void> startAsync(Months month, Integer year, UUID conductorId) {
        return runStep1(month, year, conductorId)
                .thenCompose(students -> runStep2(students));
    }

    private CompletableFuture<List<Student>> runStep1(Months month, Integer year, UUID conductorId) {
        return CompletableFuture.supplyAsync(() -> {
            return summaryStep1.execute(new EnableSummaryGenerationRequest(month, year, conductorId));
        });
    }

    private CompletableFuture<Void> runStep2(List<Student> students) {
        return CompletableFuture.runAsync(() -> {
            summaryStep2.execute(students);
        });
    }
}

