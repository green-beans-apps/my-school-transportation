package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ConductorSchema;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import com.greenbeansapps.myschooltransportation.main.security.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController()
@RequestMapping("/auth")
public class LoginController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDtoLogin data, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      ErrorResponse errorResponse = new ErrorResponse();
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    try {
      var userNamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
      var auth = this.authenticationManager.authenticate(userNamePassword);
      var conductorSchema = (ConductorSchema) auth.getPrincipal();
      var token = this.tokenService.generateToken(conductorSchema);
      return ResponseEntity.ok(new LoginResponseDto(token, conductorSchema.getId()));
    } catch (RuntimeException err) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error: Invalid username or password");
    }
  }

  @Data
  public static class AuthenticationDtoLogin {
    @NotBlank
    private String login;

    @NotBlank()
    private String password;
  }

  public record LoginResponseDto(String token, UUID conductorId) {
  }
}
