package com.example.Mutantes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "dna_records",
        indexes = {
                @Index(name = "idx_dna_hash", columnList = "dna_hash"),
                @Index(name = "idx_is_mutant", columnList = "is_mutant")
        })
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dna_hash", nullable = false, unique = true, length = 64)
    private String dnaHash;

    @Column(name = "is_mutant", nullable = false)
    private boolean isMutant;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
