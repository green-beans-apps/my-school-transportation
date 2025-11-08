package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateConductorRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateParametersConductorRequest;
import com.greenbeansapps.myschooltransportation.implementation.usecases.CreateConductorUseCaseImpl;
import com.greenbeansapps.myschooltransportation.implementation.usecases.CreateParametersConductorUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController()
@RequestMapping("/conductor")
public class CreateConductorController {

  @Autowired
  private CreateConductorUseCaseImpl createConductorUseCase;

  @Autowired
  CreateParametersConductorUseCaseImpl createParametersConductorUseCase;

  @PostMapping()
  public ResponseEntity create(@RequestBody @Valid CreateConductorDto data, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      ErrorResponse errorResponse = new ErrorResponse();
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    Conductor conductor = this.createConductorUseCase.execute(new CreateConductorRequest(data.id, data.name, data.email, data.password, data.cpf));
    this.createParametersConductorUseCase.execute(new CreateParametersConductorRequest(conductor.getId(), data.percentContractTermination));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Data
  public static class CreateConductorDto {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @CPF
    private String cpf;

    private Float percentContractTermination;
  }
}
