package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.EnableSummaryGenerationRequest;

import java.util.List;

public interface EnableSummaryGeneration {
    public List<Student> execute(EnableSummaryGenerationRequest enableSummaryGenerationRequest);
}
