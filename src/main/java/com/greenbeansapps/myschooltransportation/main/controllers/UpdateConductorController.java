package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.dto.ConductorProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateConductorRequest;
import com.greenbeansapps.myschooltransportation.implementation.usecases.UpdateConductorUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/conductor")
public class UpdateConductorController {

    @Autowired
    private UpdateConductorUseCaseImpl updateConductorUseCase;

    @PutMapping("")
    public ResponseEntity updateConductor (@RequestBody @Valid UpdateConductorDto updateConductorDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        var updateConductor = updateConductorUseCase.execute(new UpdateConductorRequest(updateConductorDto.conductorId, updateConductorDto.name, updateConductorDto.email));
        var newConductor = new ConductorProjectionDto(updateConductor.getId(), updateConductor.getName(), updateConductor.getEmail(), updateConductor.getCpf());
        return ResponseEntity.status(HttpStatus.OK).body(newConductor);
    }

    public record UpdateConductorDto(@NotNull UUID conductorId, @NotBlank String name, @NotBlank String email) {}

}
