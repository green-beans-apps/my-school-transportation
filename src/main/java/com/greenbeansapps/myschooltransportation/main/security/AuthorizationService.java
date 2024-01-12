package com.greenbeansapps.myschooltransportation.main.security;

import com.greenbeansapps.myschooltransportation.infra.repositories.impl.ConductorRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

  @Autowired
  private ConductorRepositoryJPA conductorRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.conductorRepo.findBycpfUserDetails(username);
  }
}
