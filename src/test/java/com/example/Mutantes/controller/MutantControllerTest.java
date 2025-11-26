package com.example.Mutantes.controller;

import com.example.Mutantes.tool.CalculatorDnaHash;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MutantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /dna/mutant debe retornar 200 para mutante")
    void testCheckMutant_ReturnOk_WhenIsMutant() throws Exception {
        String jsonRequest = """
           {
             "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
           }
           """;

        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /dna/mutant debe retornar 403 para humano")
    void testCheckMutant_ReturnForbidden_WhenIsHuman() throws Exception {
        String jsonRequest = """
            {
              "dna": ["CTGCGA","CAGTGC","TTATGT","AGAAGG","GCCCTA","TCACTG"]
            }
            """;

        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /dna/mutant debe retornar 400 para error en validacion de caracteres en matriz")
    void testCheckMutant_ReturnBadRequest_WithInvalidCharacters() throws Exception {
        String jsonRequest = """
            {
              "dna": ["ATXCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
            }
            """;

        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /dna/mutant debe retornar 400 para matriz vacía")
    void testCheckMutant_ReturnBadRequest_WithEmptyMatrix() throws Exception {
        String jsonRequest = """
            {
              "dna": []
            }
            """;

        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /dna/mutant debe retornar 400 para ADN nulo")
    void testCheckMutant_ReturnBadRequest_WithNullDna() throws Exception {
        String jsonRequest = """
        {
          "dna": null
        }
        """;

        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /dna/mutant debe retornar 400 para error con fila nula en matriz")
    void testCheckMutant_ReturnBadRequest_WithNullRow() throws Exception {
        String jsonRequest = """
            {
              "dna": ["ATCCGA",null,"TTATGT","AGAAGG","CCCCTA","TCACTG"]
            }
            """;

        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /dna/mutant debe retornar 400 para error en validacion de tamaño en matriz")
    void testCheckMutant_ReturnBadRequest_WithSmallMatrix() throws Exception {
        String jsonRequest = """
            {
              "dna": ["ATC","CAG","TTA"]
            }
            """;

        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /stats debe retornar 200 OK")
    void testGetStats_ReturnOk() throws Exception {
        mockMvc.perform(get("/dna/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /health debe retornar 200 OK y el estado UP")
    void testHealthEndpoint_ReturnOk() throws Exception {
        mockMvc.perform(get("/dna/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("DELETE /dna/{hash} debe retornar 204 si se borra")
    void testDeleteByHash_ReturnNoContent_WhenExists() throws Exception {
        String[] dna = {
                "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"
        };

        String jsonRequest = """
       {
         "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
       }
       """;

        mockMvc.perform(post("/dna/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        String hash = CalculatorDnaHash.sha256(dna);

        mockMvc.perform(delete("/dna/" + hash))
                .andExpect(status().isNoContent());
    }

}
