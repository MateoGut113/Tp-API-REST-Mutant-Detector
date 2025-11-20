package com.example.Mutantes.controller;

import com.example.Mutantes.dto.DnaRequest;
import com.example.Mutantes.dto.ErrorResponse;
import com.example.Mutantes.dto.HealthResponse;
import com.example.Mutantes.dto.StatsResponse;
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


    @Operation(summary = "Analizador de ADN", description = "Analiza un ADN, validando a la matriz y su contenido")
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
            description = "Devuelve una sumatoria de todos los dna almacenados en la base de datos, ya sea mutante o no, que pueden ser filtradas por rango de fechas")
    @ApiResponse(responseCode = "200", description = "Estadistica completada")
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
    public HealthResponse health() {
        return new HealthResponse("UP");
    }

    @Operation(summary = "Eliminar DNA", description = "Elimina un ADN de la base de datos, si existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Borrado exitoso"),
            @ApiResponse(responseCode = "404", description = "No se encontró a un ADN con ese hash",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @DeleteMapping("/{hash}")
    public ResponseEntity<Void> deleteByHash(@PathVariable String hash) {
        mutantService.deleteByHash(hash);
        return ResponseEntity.noContent().build();
    }

}
