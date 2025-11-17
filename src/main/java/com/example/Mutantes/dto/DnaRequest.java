package com.example.Mutantes.dto;

import com.example.Mutantes.validation.ValidDnaSequence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record DnaRequest(
        @NotNull
        @NotEmpty
        @ValidDnaSequence
        @Schema(description = "DNA a ",
                example = "\"dna\": [\n" +
                        "  \"ATGCGA\",\n" +
                        "  \"CAGTGC\",\n" +
                        "  \"TTATGT\",\n" +
                        "  \"AGAAGG\",\n" +
                        "  \"CCCCTA\",\n" +
                        "  \"TCACTG\"\n" +
                        "  ]",
                required = true)
        String[] dna
) { }
