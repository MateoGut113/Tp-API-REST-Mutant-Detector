package com.example.Mutantes.service;

import com.example.Mutantes.dto.StatsResponse;
import com.example.Mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class StatsService {
    private final DnaRecordRepository dnaRecordRepository;

    @Cacheable(value = "statsCache", key = "{#startDate, #endDate}")
    public StatsResponse obtenerEstadisticas(LocalDate startDate, LocalDate endDate){
        Long mutantes;
        Long humanos;

        if (startDate != null && endDate != null) {
            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(23, 59, 59);

            mutantes = dnaRecordRepository.countByIsMutantAndCreatedAtBetween(true, start, end);
            humanos  = dnaRecordRepository.countByIsMutantAndCreatedAtBetween(false, start, end);
        } else {
            mutantes = dnaRecordRepository.countByIsMutant(true);
            humanos  = dnaRecordRepository.countByIsMutant(false);
        }

        double ratio = humanos == 0 ? 0.0 : (double) mutantes / humanos;
        return StatsResponse.fromEntity(mutantes, humanos, ratio);
    }
}
