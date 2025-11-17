package com.example.Mutantes.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class CalculatorDnaHash {

    private CalculatorDnaHash() {}

    public static String sha256(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Concatenamos todas las filas del ADN en un solo string
            String joined = String.join("", Arrays.asList(dna));

            byte[] encodedHash = digest.digest(joined.getBytes(StandardCharsets.UTF_8));

            // Convertimos a hexadecimal
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculando SHA-256", e);
        }
    }
}