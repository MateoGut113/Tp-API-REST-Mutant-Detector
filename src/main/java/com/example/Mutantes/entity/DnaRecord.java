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
                @Index(name = "idx_dna_hash", columnList = "dnaHash", unique = true),
                @Index(name = "idx_is_mutant", columnList = "isMutant")
        })
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String dnaHash;

    @Column(nullable = false)
    private boolean isMutant;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
