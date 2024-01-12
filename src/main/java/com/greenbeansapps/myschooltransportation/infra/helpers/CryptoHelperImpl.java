package com.greenbeansapps.myschooltransportation.infra.helpers;

import com.greenbeansapps.myschooltransportation.implementation.protocols.helpers.CryptoHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CryptoHelperImpl implements CryptoHelper {
  @Override
  public String generateRash(String data) {
    return new BCryptPasswordEncoder().encode(data);
  }
}
