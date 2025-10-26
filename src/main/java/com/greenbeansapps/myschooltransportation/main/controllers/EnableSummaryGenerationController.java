package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidMonthException;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.EnableSummaryGenerationRequest;
import com.greenbeansapps.myschooltransportation.implementation.usecases.EnableSummaryGenerationUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SummaryGeneration")
public class EnableSummaryGenerationController {

    @Autowired
    private EnableSummaryGenerationUseCaseImpl enableSummaryGenerationUseCaseImpl;

    @PostMapping()
    public ResponseEntity EnableSummaryGeneration(@RequestBody @Valid EnableSummaryGenerationDto summaryGenerationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Months month = validateMonth(summaryGenerationDto.referenceMonth);

        enableSummaryGenerationUseCaseImpl.execute(new EnableSummaryGenerationRequest(month, summaryGenerationDto.referenceYear));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    public record EnableSummaryGenerationDto(@NotBlank String referenceMonth, @NotNull Integer referenceYear) {}

    private Months validateMonth(String paymentMonth) {
        try {
            return Months.valueOf(paymentMonth.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidMonthException();
        }
    }
}
