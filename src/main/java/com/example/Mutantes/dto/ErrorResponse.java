package com.example.Mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        @Schema(description = "Tiempo exacto en que ocurrió el error",
                example = "2025-11-09T23:51:42.2327012")
        LocalDateTime timestamp,

        @Schema(description = "Estado del error",
                example = "400")
        int status,

        @Schema(description = "Mensaje de tipo de error",
                example = "Bad Request")
        String error,

        @Schema(description = "Lista de detalles sobre el error",
                example = "Invalid DNA sequence: must be a square NxN matrix (minimum 4x4)" +
                        "with only A, T, C, G characters")
        List<String> detalles,

        @Schema(description = "Ruta en la cual se produjo el error",
                example = "/api/mutant")
        String ruta
) {
    public static ErrorResponse simple(int status, String error, String detalles, String ruta) {
        return new ErrorResponse(LocalDateTime.now(), status, error, List.of(detalles), ruta);
    }

    public static ErrorResponse of(int status, String error, List<String> detalles, String ruta) {
        return new ErrorResponse(LocalDateTime.now(), status, error, detalles, ruta);
    }
}
