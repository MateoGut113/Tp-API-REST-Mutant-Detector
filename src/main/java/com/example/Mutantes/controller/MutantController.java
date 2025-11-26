package com.example.Mutantes.controller;

import com.example.Mutantes.dto.*;
import com.example.Mutantes.service.MutantService;
import com.example.Mutantes.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Tag(name = "Mutant Detector API", description = "\nSistema de control de ADN, para detección de mutantes y consulta de estadísticas.")
@RequestMapping("/dna")
@RestController
public class MutantController {
    private final MutantService mutantService;
    private final StatsService statsService;


    @Operation(summary = "Analizador de ADN", description = "Analiza un ADN, validando a la matriz y su contenido. Un humano es mutante si tiene más de una secuencia de 4 letras iguales en dirección horizontal, vertical o diagonal. " +
            "El ADN se representa como una matriz NxN con caracteres A, T, C, G.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ADN Mutante - Se encontraron más de una secuencia de 4 letras iguales"),
            @ApiResponse(responseCode = "400", description = "Validacion de matriz denegada - ADN nulo, vacío, matriz no cuadrada o caracteres inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "403", description = "ADN Humano - Se encontró una o ninguna secuencia de 4 letras iguales",
                    content = @Content())
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@Validated @RequestBody DnaRequest request) throws Exception {
        CompletableFuture<Boolean> future = mutantService.isMutant(request.dna());

        boolean esMutante = future.get();
        if (esMutante) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @Operation(summary = "Estadisticas sobre mutantes",
            description = "Devuelve una sumatoria de todos los AND verificados, cantidad de ADN mutantes detectados, cantidad de ADN humanos detectados, " +
                    "y el ratio entre mutantes y humanos (count_mutant_dna / count_human_dna), que pueden ser filtradas por rango de fechas.\n " +
                    "Formato de fecha valido: YYYY-MM-DD. Por ejemplo: 2025-11-20")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadistica completada"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha invalido",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDateSchema.class)
                    ))
    })
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        StatsResponse estadisticas = statsService.obtenerEstadisticas(startDate, endDate);
        return ResponseEntity.ok(estadisticas);
    }

    @Operation(summary = "Endpoint de salud", description = "Sirve como punto de verificación de estado de la aplicación")
    @ApiResponse(responseCode = "200", description = "Verificacion completada")
    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        HealthResponse response = new HealthResponse("UP");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar DNA", description = "Elimina un ADN mediante el hash (SHA-256 del ADN), si existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Borrado exitoso"),
            @ApiResponse(responseCode = "404", description = "No se encontró a un ADN con ese hash",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Error404Schema.class)
                    ))
    })
    @DeleteMapping("/{hash}")
    public ResponseEntity<Void> deleteByHash(@PathVariable String hash) {
        mutantService.deleteByHash(hash);
        return ResponseEntity.noContent().build();
    }

}
