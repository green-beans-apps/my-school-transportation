package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.EnableSummaryGenerationRequest;

public interface EnableSummaryGeneration {
    public void execute(EnableSummaryGenerationRequest enableSummaryGenerationRequest);
}
