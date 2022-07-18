package com.meli.dnaanalyzer.controller.impl;

import com.google.gson.Gson;
import com.meli.dnaanalyzer.model.APIError;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.model.request.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MimeTypeUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("dev")
class MutantControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void setup() {
        gson = new Gson();
    }

    @Test
    void mutant() throws Exception {
        Person person = new Person();
        person.setDna(new String[]{
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"});
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/mutant/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(person)))
                .andExpect(status().isOk());
    }

    @Test
    void mutantValidaArray() throws Exception {
        Person person = new Person();
        person.setDna(new String[]{
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA"});
        MvcResult  mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/mutant/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(person)))
                .andExpect(status().isBadRequest())
                .andReturn();
        APIError apiError = gson.fromJson(mvcResult.getResponse().getContentAsString(), APIError.class);
        assertThat(apiError).isNotNull();
        assertThat(apiError.getErrors()).isNotNull();
        assertThat(apiError.getErrors().get(0)).isEqualTo("The array does not have a valid length");
    }

    @Test
    void mutantValidaContent() throws Exception {
        Person person = new Person(new String[]{
                "TTGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "GCDCTA",
                "TCACTG"});
        MvcResult  mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/mutant/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(person)))
                .andExpect(status().isBadRequest())
                .andReturn();
        APIError apiError = gson.fromJson(mvcResult.getResponse().getContentAsString(), APIError.class);
        assertThat(apiError).isNotNull();
        assertThat(apiError.getErrors()).isNotNull();
        assertThat(apiError.getErrors().get(0)).isEqualTo("GCDCTA is not appropriate");
        apiError.setErrors(null);
        assertThat(apiError.getErrors()).isNull();
    }

    @Test
    void human() throws Exception {
        Person person = Person.builder().dna((new String[]{
                "TTGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "GCCCTA",
                "TCACTG"})).build();
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/mutant/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(person)))
                .andExpect(status().isForbidden());
    }

    @Test
    void stats() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/stats/").accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        StatisticsDTO statisticsDTO = gson.fromJson(mvcResult.getResponse().getContentAsString(), StatisticsDTO.class);
        assertThat(statisticsDTO).isNotNull();
        assertThat(statisticsDTO.getRatio()).isEqualTo(1.0);
        assertThat(statisticsDTO.getCountMutantDna()).isEqualTo(1L);
        assertThat(statisticsDTO.getCountHumanDna()).isEqualTo(1L);
    }
}