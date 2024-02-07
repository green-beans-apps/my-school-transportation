package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.dto.ConductorProjectionDto;
import com.greenbeansapps.myschooltransportation.implementation.usecases.GetConductorByIdUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/conductor")
public class GetConductorByIdController {
    @Autowired
    private GetConductorByIdUseCaseImpl getConductorByIdUseCase;

    @GetMapping("/{conductorId}")
    public ResponseEntity<ConductorProjectionDto> getConductorById(@PathVariable("conductorId") UUID conductorId) {
        var getConductor = this.getConductorByIdUseCase.execute(conductorId);
        return ResponseEntity.status(HttpStatus.OK).body(getConductor);
    }
}
