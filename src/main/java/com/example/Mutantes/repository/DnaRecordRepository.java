package com.example.Mutantes.repository;

import com.example.Mutantes.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, String> {

    Optional<DnaRecord> findByDnaHash(String dnaHash);

    long countByIsMutant(boolean isMutant);

}
