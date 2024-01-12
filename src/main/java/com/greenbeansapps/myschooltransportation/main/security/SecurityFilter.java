package com.greenbeansapps.myschooltransportation.main.security;

import com.greenbeansapps.myschooltransportation.infra.repositories.impl.ConductorRepositoryJPA;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


  @Autowired
  TokenService tokenService;

  @Autowired
  ConductorRepositoryJPA conductorRepo;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);
    if (token != null) {
      var login = this.tokenService.validateToken(token);
      UserDetails user = this.conductorRepo.findBycpfUserDetails(login);

      var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken( HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if(authHeader == null) {
      return null;
    }
    return authHeader.replace("Bearer", "");
  }
}
