package com.example.Mutantes.service;

import com.example.Mutantes.entity.DnaRecord;
import com.example.Mutantes.repository.DnaRecordRepository;
import com.example.Mutantes.util.CalculatorDnaHash;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MutantService {
    private final DnaRecordRepository dnaRecordRepository;
    private final MutantDetector mutantDetector;

    public boolean isMutant(String[] dna) {
        String hash = CalculatorDnaHash.sha256(dna);

        Optional<DnaRecord> cached = dnaRecordRepository.findByDnaHash(hash);
        if (cached.isPresent()) {
            return cached.get().isMutant();
        }

        boolean result = mutantDetector.isMutant(dna);

        DnaRecord record = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(result)
                .createdAt(LocalDateTime.now())
                .build();
        dnaRecordRepository.save(record);

        return result;
    }
}
