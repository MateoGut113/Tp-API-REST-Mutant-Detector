package com.example.Mutantes.service;

import com.example.Mutantes.dto.StatsResponse;
import com.example.Mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Debe verificar la division por 1")
    void testCountMutants() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(5L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(1L);

        StatsResponse response = statsService.obtenerEstadisticas(null, null);

        assertEquals(5L, response.count_mutant_dna());
        assertEquals(1L, response.count_human_dna());
        assertEquals(5.0, response.ratio());
    }

    @Test
    @DisplayName("Debe verificar la division por 0")
    void testCountHumansZero() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(5L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.obtenerEstadisticas(null, null);

        assertEquals(5L, response.count_mutant_dna());
        assertEquals(0L, response.count_human_dna());
        assertEquals(0.0, response.ratio());
    }

    @Test
    @DisplayName("Debe verificar la division con un numerador igual 0")
    void testCountMutantsZero() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(5L);

        StatsResponse response = statsService.obtenerEstadisticas(null, null);

        assertEquals(0L, response.count_mutant_dna());
        assertEquals(5L, response.count_human_dna());
        assertEquals(0.0, response.ratio());
    }

    @Test
    @DisplayName("Debe verificar la division entre dos 0")
    void testCountMutantsZeroAndHumansZero() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.obtenerEstadisticas(null, null);

        assertEquals(0L, response.count_mutant_dna());
        assertEquals(0L, response.count_human_dna());
        assertEquals(0.0, response.ratio());
    }

    @Test
    @DisplayName("Debe verificar la division entre dos numeros iguales")
    void testCountMutantsAndHumansEquals() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(10L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(10L);

        StatsResponse response = statsService.obtenerEstadisticas(null, null);

        assertEquals(10L, response.count_mutant_dna());
        assertEquals(10L, response.count_human_dna());
        assertEquals(1.0, response.ratio());
    }

    @Test
    @DisplayName("Debe verificar ratio extenso")
    void testCountExtensiveRatio() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(3L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(7L);

        StatsResponse response = statsService.obtenerEstadisticas(null, null);

        assertEquals(3L, response.count_mutant_dna());
        assertEquals(7L, response.count_human_dna());
        assertEquals(0.42857142857142855, response.ratio());
    }

}
