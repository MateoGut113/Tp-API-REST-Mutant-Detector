package com.example.Mutantes.service;

import com.example.Mutantes.util.ConvertCharDna;
import org.springframework.stereotype.Component;

@Component
public class MutantDetector {
    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        char[][] matrix = ConvertCharDna.toMatrix(dna);
        int sequences = countSequences(matrix);
        return sequences > 1;
    }

    private int countSequences(char[][] matrix) {
        int n = matrix.length;
        int sequenceCount = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                char base = matrix[row][col];

                // Horizontal →
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                }

                // Vertical ↓
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                }

                // Diagonal descendente ↘
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                }

                // Diagonal ascendente ↗
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return sequenceCount;
                    }
                }

                // Si ya encontramos 2 secuencias, podemos cortar
                if (sequenceCount > 1) {
                    return sequenceCount;
                }
            }
        }
        return sequenceCount;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }
    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }
    private boolean checkDiagonalDescending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }
    private boolean checkDiagonalAscending(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }

}
