package com.example.Mutantes.controller;

import com.example.Mutantes.dto.DnaRequest;
import com.example.Mutantes.dto.ErrorResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@Tag(name = "Mutantes", description = "Sistema de control de ADN, para distinguir humamos de mutantes.")
@RequestMapping("/dna")
@RestController
public class MutantController {
    private final MutantService mutantService;
    private final StatsService statsService;


    @Operation(summary = "Analyze DNA", description = "Analiza un ADN, validando a la matriz y su contenido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ADN Mutante"),
            @ApiResponse(responseCode = "400", description = "Validacion de matriz denegada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "403", description = "ADN Humano")
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@Validated @RequestBody DnaRequest request) {
        boolean esMutante = mutantService.isMutant(request.dna());

        if (esMutante) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @Operation(summary = "Estadisticas sobre mutantes", description = "Devuelve una sumatoria de todos los dna almacenados en la base de datos, ya sea mutante o no")
    @ApiResponses(value =
                @ApiResponse(responseCode = "200", description = "Estadistica completada")
    )
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse estadisticas = statsService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }

}
