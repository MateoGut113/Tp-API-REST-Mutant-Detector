package com.example.Mutantes.service;

import com.example.Mutantes.entity.DnaRecord;
import com.example.Mutantes.exception.HashNotFoundException;
import com.example.Mutantes.repository.DnaRecordRepository;
import com.example.Mutantes.tool.CalculatorDnaHash;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class MutantService {
    private final DnaRecordRepository dnaRecordRepository;
    private final MutantDetector mutantDetector;

    @Async
    public CompletableFuture<Boolean> isMutant(String[] dna) {
        String hash = CalculatorDnaHash.sha256(dna);

        Optional<DnaRecord> cached = dnaRecordRepository.findByDnaHash(hash);
        if (cached.isPresent()) {
            return CompletableFuture.completedFuture(cached.get().isMutant());
        }

        boolean result = mutantDetector.isMutant(dna);

        DnaRecord record = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(result)
                .createdAt(LocalDateTime.now())
                .build();
        dnaRecordRepository.save(record);

        return CompletableFuture.completedFuture(result);
    }

    public void deleteByHash(String hash) {
        DnaRecord record = dnaRecordRepository.findByDnaHash(hash)
                .orElseThrow(() -> new HashNotFoundException("No existe un ADN con el hash " +hash));
        dnaRecordRepository.delete(record);
    }
}
