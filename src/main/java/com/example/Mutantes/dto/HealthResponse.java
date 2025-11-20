package com.example.Mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record HealthResponse(
        @NotNull
        @Schema(description = "Estado de la aplicacion",
                example = "UP")
        String status,

        @NotNull
        @Schema(description = "Tiempo exacto en que ocurrió la solicitud",
                example = "2025-11-09T23:51:42.2327012")
        String timestamp
) {
    public HealthResponse(String status) {
        this(status, LocalDateTime.now()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
