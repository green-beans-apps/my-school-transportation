package com.greenbeansapps.myschooltransportation.infra.helpers;

import com.greenbeansapps.myschooltransportation.implementation.protocols.helpers.CryptoHelper;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CryptoHelperImpl implements CryptoHelper {
  @Override
  public String generateRash(String data) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = digest.digest(data.getBytes());

    // Converter os bytes do hash para uma representação hexadecimal
    StringBuilder hexString = new StringBuilder();
    for (byte b : hashBytes) {
      String hex = Integer.toHexString(0xFF & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }

    return hexString.toString();
  }
}
