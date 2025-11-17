package com.example.Mutantes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {
    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length < 4) return false;

        int n = dna.length;
        for (String row : dna) {
            if (row == null || row.length() != n) return false;
            if (!row.matches("[ATCG]+")) return false;
        }
        return true;
    }

}
