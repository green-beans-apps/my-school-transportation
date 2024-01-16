package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.exeptions.CpfAlreadyRegisteredException;
import com.greenbeansapps.myschooltransportation.implementation.usecases.CreateConductorUseCaseImpl;
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

@RestController()
@RequestMapping("/conductor")
public class RegisterConductorController {

  @Autowired
  private CreateConductorUseCaseImpl createConductorUseCase;

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody @Valid RegisterConductorDto data, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      ErrorResponse errorResponse = new ErrorResponse();
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    try {
      this.createConductorUseCase.execute(data.getName(), data.getEmail(), data.getPassword(), data.getCpf());
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (CpfAlreadyRegisteredException cpfAlreadyRegisteredException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cpfAlreadyRegisteredException.getMessage());
    } catch (Error err) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
    }
  }
  @Data
  public static class RegisterConductorDto {

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

  }
}
