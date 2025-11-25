package com.example.Mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record Error404Schema(
        @Schema(description = "Tiempo exacto en que ocurrió el error",
                example = "2025-11-09T23:51:42.2327012")
        LocalDateTime timestamp,

        @Schema(description = "Estado del error",
                example = "404")
        int status,

        @Schema(description = "Mensaje de tipo de error",
                example = "Hash Not Found Exception")
        String error,

        @Schema(description = "Lista de detalles sobre el error",
                example = "No se encontró al hash: 12345")
        List<String> detalles,

        @Schema(description = "Ruta en la cual se produjo el error",
                example = "/dna/12345")
        String ruta
) {
}
