package com.example.Mutantes.dto;

import com.example.Mutantes.validation.ValidDnaSequence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record DnaRequest(
        @NotNull
        @NotEmpty
        @ValidDnaSequence
        @Schema(
                description = "Secuencia de ADN representada como matriz NxN",
                example = "[\"ATGCGA\", \"CAGTGC\", \"TTATGT\", \"AGAAGG\", \"CCCCTA\", \"TCACTG\"]",
                required = true
        )
        String[] dna
) { }
