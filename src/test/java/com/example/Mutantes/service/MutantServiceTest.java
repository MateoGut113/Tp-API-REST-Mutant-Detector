package com.example.Mutantes.service;

import com.example.Mutantes.entity.DnaRecord;
import com.example.Mutantes.exception.HashNotFoundException;
import com.example.Mutantes.repository.DnaRecordRepository;
import com.example.Mutantes.tool.CalculatorDnaHash;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTest {
    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @Mock
    private MutantDetector mutantDetector;

    @InjectMocks
    private MutantService mutantService;

    @Test
    @DisplayName("Debe devolver resultado cacheado si existe en repositorio")
    void testIsMutant_ReturnsCachedValue() throws Exception{
        String[] dna = {"ATGC","CAGT","TTAT","AGAA"};
        String hash = CalculatorDnaHash.sha256(dna);

        DnaRecord cachedRecord = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(true)
                .createdAt(LocalDateTime.now())
                .build();

        when(dnaRecordRepository.findByDnaHash(hash)).thenReturn(Optional.of(cachedRecord));

        CompletableFuture<Boolean> future = mutantService.isMutant(dna);

        boolean result = future.get();

        assertTrue(result);
        verify(mutantDetector, never()).isMutant(any());
    }

    @Test
    @DisplayName("Debe calcular resultado y guardar si no existe en repositorio")
    void testIsMutant_ComputesAndSavesWhenNotCached() throws Exception{
        String[] dna = {"ATGC","CAGT","TTAT","AGAA"};
        String hash = CalculatorDnaHash.sha256(dna);

        when(dnaRecordRepository.findByDnaHash(hash)).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);

        CompletableFuture<Boolean> future = mutantService.isMutant(dna);

        boolean result = future.get();

        assertFalse(result);
        verify(dnaRecordRepository).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Verificar funcionamiento de CalculatorDnaHash")
    void testSha256Calculation() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAA"
        };

        String expectedHash = "f3b2039ef249003aa750da7702ecc90db18d5ec89ed99e882ac4e74f891f13b8";

        String actualHash = CalculatorDnaHash.sha256(dna);

        assertEquals(expectedHash, actualHash);
    }

    @Test
    @DisplayName("Dos DnaRecord con mismo hash y resultado deben ser iguales")
    void testEqualsWithSameHashAndResult() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAA"
        };
        String hash = CalculatorDnaHash.sha256(dna);

        DnaRecord record1 = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(true)
                .createdAt(LocalDateTime.now())
                .build();

        DnaRecord record2 = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(true)
                .createdAt(LocalDateTime.now())
                .build();

        assertEquals(record1, record2);
        assertEquals(record1.hashCode(), record2.hashCode());
    }

    @Test
    @DisplayName("Dos DnaRecord con distinto hash deben ser diferentes")
    void testNotEqualsWithDifferentHash() {
        String[] dna1 = {"ATGC","CAGT","TTAT","AGAA"};
        String[] dna2 = {"ATGC","CAGT","TTAT","CCCC"};

        String hash1 = CalculatorDnaHash.sha256(dna1);
        String hash2 = CalculatorDnaHash.sha256(dna2);

        DnaRecord record1 = DnaRecord.builder()
                .dnaHash(hash1)
                .isMutant(true)
                .createdAt(LocalDateTime.now())
                .build();

        DnaRecord record2 = DnaRecord.builder()
                .dnaHash(hash2)
                .isMutant(true)
                .createdAt(LocalDateTime.now())
                .build();

        assertNotEquals(record1, record2);
    }

    @Test
    @DisplayName("Debe borrar el DNA si existe")
    void testDeleteByHashSuccess() {
        DnaRecord record = new DnaRecord();
        record.setDnaHash("abc123");

        when(dnaRecordRepository.findByDnaHash("abc123"))
                .thenReturn(Optional.of(record));

        mutantService.deleteByHash("abc123");

        verify(dnaRecordRepository).delete(record);
    }

    @Test
    @DisplayName("Debe lanzar excepción si el DNA no existe")
    void testDeleteByHashNotFound() {
        when(dnaRecordRepository.findByDnaHash("abc123"))
                .thenReturn(Optional.empty());

        assertThrows(HashNotFoundException.class,
                () -> mutantService.deleteByHash("abc123"));
    }

}
