package com.greenbeansapps.myschooltransportation.implementation.protocols.helpers;

import java.security.NoSuchAlgorithmException;

public interface CryptoHelper {
    public String generateRash(String data) throws NoSuchAlgorithmException;
}
