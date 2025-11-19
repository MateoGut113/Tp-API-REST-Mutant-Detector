package com.example.Mutantes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record StatsResponse(
        @JsonProperty("count_mutant_dna")
        @Schema(description = "Cantidad de DNAs mutantes detectados",
                example = "50",
                required = true)
        Long count_mutant_dna,

        @JsonProperty("count_human_dna")
        @Schema(description = "Cantidad de DNAs humanos detectados",
                example = "100",
                required = true)
        Long count_human_dna,

        @Schema(description = "Ratio = mutantes / humanos",
                example = "0.5",
                required = true)
        Double ratio
) {
    public static StatsResponse fromEntity(Long mutantes, Long humanos, Double ratio) {
        return new StatsResponse(mutantes, humanos, ratio);
    }
}
