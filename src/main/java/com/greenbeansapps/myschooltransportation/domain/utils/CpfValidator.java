package com.greenbeansapps.myschooltransportation.domain.utils;

public class CpfValidator {
  public static boolean execute(String cpf) {
    String cleanedCpf = cpf.replaceAll("[^0-9]", "");

    if (cleanedCpf.length() != 11) {
      return false;
    }
    if (!isValidCpf(cleanedCpf)) {
      return false;
    }
    return true;
  }

  private static boolean isValidCpf(String cpf) {
    int[] digits = new int[11];
    for (int i = 0; i < 11; i++) {
      digits[i] = Character.getNumericValue(cpf.charAt(i));
    }

    boolean allDigitsEqual = true;
    for (int i = 1; i < 11; i++) {
      if (digits[i] != digits[0]) {
        allDigitsEqual = false;
        break;
      }
    }
    if (allDigitsEqual) {
      return false;
    }

    int sum = 0;
    for (int i = 0; i < 9; i++) {
      sum += digits[i] * (10 - i);
    }
    int firstVerifierDigit = (sum * 10) % 11;
    if (firstVerifierDigit == 10) {
      firstVerifierDigit = 0;
    }
    if (firstVerifierDigit != digits[9]) {
      return false;
    }

    sum = 0;
    for (int i = 0; i < 10; i++) {
      sum += digits[i] * (11 - i);
    }
    int secondVerifierDigit = (sum * 10) % 11;
    if (secondVerifierDigit == 10) {
      secondVerifierDigit = 0;
    }
    if (secondVerifierDigit != digits[10]) {
      return false;
    }

    return true;
  }

}