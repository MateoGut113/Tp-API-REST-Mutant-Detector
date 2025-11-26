package com.example.Mutantes.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class MutantDetectorTest {
    //Casos Mutantes (debe retornar true)
    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontal y diagonal")
    void testMutantWithHorizontalAndDiagonalSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias verticales")
    void testMutantWithVerticalSequences() {
        String[] dna = {
                "GTGCGA",
                "CAGTGC",
                "ATATGT",
                "AGAAGG",
                "ACCCTA",
                "ACACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontales")
    void testMutantWithMultipleHorizontalSequences(){
        String[] dna = {
                "GTGCTA",
                "CAGTGC",
                "ATTTTG",
                "TGAAGG",
                "CCCCTA",
                "ACACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias diagonales ascendientes y descendientes")
    void testMutantWithBothDiagonals(){
        String[] dna = {
                "GTGTCA",
                "CATTGC",
                "ATGATG",
                "TCAGCG",
                "ACGCGA",
                "ACACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencias horizontales y verticales")
    void testMutantWithHorizontalAndVerticalSequences() {
        String[] dna = {
                "CTGCG",
                "CAGTG",
                "TTATG",
                "AGAAG",
                "CCCCT"
        };

        MutantDetector detector = new MutantDetector();
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante en matriz grande (10x10)")
    void testMutantWithLargeDna(){
        String[] dna = {
                "GTGTCACATG",
                "CATTGCCCGA",
                "ATGATGGATG",
                "TCAGCGTTGG",
                "ACGCGAACTC",
                "ACACTGTCCG",
                "ATGATGGTTG",
                "TCAGCGAAAC",
                "ACGCGATCCA",
                "ACACTGCGAT"
        };

        MutantDetector detector = new MutantDetector();
        assertTrue(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con matriz toda igual")
    void testMutantAllSameCharacter(){
        String[] dna = {
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        };

        MutantDetector detector = new MutantDetector();
        assertTrue(detector.isMutant(dna));
    }

    //Casos Humanos (debe retornar false)
    @Test
    @DisplayName("Debe detectar humano con solo 1 secuencia encontrada")
    void testNotMutantWithOnlyOneSequence(){
        String[] dna = {
                "GTGTCA",
                "CATTGC",
                "ACGATG",
                "TTAGCG",
                "ACGCGA",
                "ACACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar humano sin secuencias encontradas")
    void testNotMutantWithNoSequences(){
        String[] dna = {
                "GTGTCA",
                "CATTGC",
                "ACGATG",
                "TTAGCG",
                "ACGCGA",
                "ACACTC"
        };

        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar humano en matriz 4x4 sin secuencias encontradas")
    void testNotMutantSmallDna(){
        String[] dna = {
                "GTGT",
                "CATT",
                "ACGA",
                "ACAC"
        };

        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    }

    //Validaciones (debe retornar false)
    /*@Test
    @DisplayName("Debe retornar error si el DNA es null, ya que de eso se encarga el DTO")
    void testNotMutantWithNullDna() {
        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(null));
    }*/

    @Test
    @DisplayName("Debe retornar false si el DNA está vacío")
    void testNotMutantWithEmptyDna() {
        String[] dna = {};
        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe retornar false si la matriz no es cuadrada (4x5)")
    void testNotMutantWithNonSquareDna() {
        String[] dna = {
                "ATGCG",
                "CAGTG",
                "TTATG",
                "CCCCT"
        };

        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    }


    /* @Test
    @DisplayName("Debe retornar error si el DNA contiene caracteres inválidos, ya que de eso se encarga el DTO")
    void testNotMutantWithInvalidCharacters() {
        String[] dna = {
                "ATGCXA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    } */

    /* @Test
    @DisplayName("Debe retornar error si alguna fila del DNA es null, ya que de eso se encarga el DTO")
    void testNotMutantWithNullRow() {
        String[] dna = {
                "ATGCGA",
                null,
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    } */

    @Test
    @DisplayName("Debe retornar false si el DNA es demasiado pequeño (3x3)")
    void testNotMutantWithTooSmallDna() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"
        };

        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    }

    //Edge Cases
    @Test
    @DisplayName("No debe detectar mutante con 1 sola secuencia de longitud 5")
    void testNotMutantWithSequenceLongerThanFour() {
        String[] dna = {
                "AAAAA",  // 5 iguales, cuenta como 1 sola secuencia
                "CCGTGC",
                "TTATGT",
                "AGAAGG",
                "TCACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertFalse(detector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con secuencia diagonal en esquina")
    void testMutantDiagonalInCorner() {
        String[] dna = {
                "ATGCGA",  // Diagonal descendente desde la esquina superior izquierda:
                "CAGTAC",
                "TTAAGT",
                "AGAAAG",
                "CCCATA",
                "TCACTG"
        };

        MutantDetector detector = new MutantDetector();
        assertTrue(detector.isMutant(dna));
    }

    //Nuevos requerimientos
    @Test
    @DisplayName("Debe loguear secuencia horizontal encontrada")
    void testHorizontalLogging() {
        Logger logger = (Logger) LoggerFactory.getILoggerFactory()
                .getLogger(MutantDetector.class.getName());
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        String[] dna = {
                "CCCC",
                "AAAA",
                "TTTT",
                "GGGG"
        };

        MutantDetector detector = new MutantDetector();
        detector.isMutant(dna);

        listAppender.list.forEach(event ->
                System.out.println("LOG: " + event.getFormattedMessage()));

        // Verificar que se logueó el mensaje esperado
        boolean found = listAppender.list.stream()
                .anyMatch(event -> event.getFormattedMessage()
                        .contains("Secuencia horizontal encontrada"));

        assertTrue(found, "Debe loguear las secuencias horizontales encontradas");
    }

    @Test
    @DisplayName("Debe loguear secuencia diagonal encontrada")
    void testDiagonalLogging() {
        Logger logger = (Logger) LoggerFactory.getILoggerFactory()
                .getLogger(MutantDetector.class.getName());
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGCA"
        };

        MutantDetector detector = new MutantDetector();
        detector.isMutant(dna);

        listAppender.list.forEach(event ->
                System.out.println("LOG: " + event.getFormattedMessage()));

        // Verificar que se logueó el mensaje esperado
        boolean found = listAppender.list.stream()
                .anyMatch(event -> event.getFormattedMessage()
                        .contains("Secuencia diagonal descendiente encontrada"));

        assertTrue(found, "Debe loguear las secuencias diagonales encontradas");
    }

}
