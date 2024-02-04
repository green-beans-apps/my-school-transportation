package com.greenbeansapps.myschooltransportation.main.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ConductorSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;
  
  private record payload(UUID id, String cpf) {
  }

  public String generateToken(ConductorSchema conductor) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.secret);
      String token =  JWT.create()
              .withIssuer("auth-api")
              .withSubject(conductor.getCpf())
              .withExpiresAt(this.generateExpirationDate())
              .sign(algorithm);
      return token;
    } catch (JWTCreationException e) {
      throw  new RuntimeException("Error wile generating token", e);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.secret);
      return JWT.require(algorithm)
              .withIssuer("auth-api")
              .build()
              .verify(token)
              .getSubject();
    } catch (JWTVerificationException err) {
      return "";
    }
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusYears(5).toInstant(ZoneOffset.of("-03:00"));
  }

}
