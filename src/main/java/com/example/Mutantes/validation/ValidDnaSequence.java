package com.example.Mutantes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDnaSequence {
    String message() default "Invalid DNA sequence: must be a square NxN matrix (minimum 4x4) with only A, T, C, G characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
