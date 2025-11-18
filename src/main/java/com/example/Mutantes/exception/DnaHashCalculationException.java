package com.example.Mutantes.exception;

import java.security.NoSuchAlgorithmException;

public class DnaHashCalculationException extends RuntimeException {
    public DnaHashCalculationException(String message, NoSuchAlgorithmException e) {
        super(message);
    }
}
