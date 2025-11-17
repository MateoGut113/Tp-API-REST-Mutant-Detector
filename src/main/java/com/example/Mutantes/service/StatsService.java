package com.example.Mutantes.service;

import com.example.Mutantes.dto.StatsResponse;
import com.example.Mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse obtenerEstadisticas(){
        Long mutantes = dnaRecordRepository.countByIsMutant(true);
        Long humanos = dnaRecordRepository.countByIsMutant(false);
        double ratio = humanos == 0 ? 0.0 : (double) mutantes / humanos;

        return StatsResponse.fromEntity(mutantes, humanos, ratio);
    }
}
