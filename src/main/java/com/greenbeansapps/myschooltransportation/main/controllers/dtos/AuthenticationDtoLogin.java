package com.greenbeansapps.myschooltransportation.main.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationDtoLogin {
  @NotBlank
  private String login;

  @NotBlank(message = "Please enter")
  private String password;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
