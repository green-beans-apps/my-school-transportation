package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.exeptions.CpfAlreadyRegisteredException;
import com.greenbeansapps.myschooltransportation.implementation.usecases.CreateConductorUseCaseImpl;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ConductorSchema;
import com.greenbeansapps.myschooltransportation.main.controllers.dtos.AuthenticationDtoLogin;
import com.greenbeansapps.myschooltransportation.main.controllers.dtos.LoginReponseDto;
import com.greenbeansapps.myschooltransportation.main.controllers.dtos.RegisterConductorDto;
import com.greenbeansapps.myschooltransportation.main.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CreateConductorUseCaseImpl createConductorUseCase;

  @Autowired
  TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid AuthenticationDtoLogin data) {
    try {
      var userNamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
      var auth = this.authenticationManager.authenticate(userNamePassword);
      var token = this.tokenService.generateToken((ConductorSchema) auth.getPrincipal());
      return ResponseEntity.ok(new LoginReponseDto(token));
    } catch (RuntimeException err) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error: Invalid username or password");
    }
  }

  // metodo usado para teste
  @PostMapping("/register")
  public ResponseEntity register(@RequestBody @Valid RegisterConductorDto data) {
    try {
      this.createConductorUseCase.execute(data.getName(), data.getEmail() ,data.getPassword(), data.getCpf());
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (CpfAlreadyRegisteredException cpfAlreadyRegisteredException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cpfAlreadyRegisteredException.getMessage());
    } catch (Error err)  {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
  }
}
