package com.example.Mutantes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDnaSequence {
    String message() default "Secuencia DNA inválida: debe ser una matriz NxN (minimo 4x4) y solamente con caracteres A, T, C, G";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
