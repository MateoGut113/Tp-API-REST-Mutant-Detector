package com.example.Mutantes.tool;

public final class ConvertCharDna {
    private ConvertCharDna() {}
    public static char[][] toMatrix(String[] dna) {
        int n = dna.length;
        char[][] matrix = new char[n][n];

        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        return matrix;
    }
}