package com.greenbeansapps.myschooltransportation.main.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("")
public class HealthCheck {
  @GetMapping("/health")
  public String health() {
    return "Ok";
  }
}
