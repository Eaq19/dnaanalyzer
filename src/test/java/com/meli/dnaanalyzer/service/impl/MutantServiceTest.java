package com.meli.dnaanalyzer.service.impl;

import com.meli.dnaanalyzer.model.dao.PersonDAO;
import com.meli.dnaanalyzer.service.PersonServiceInt;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
class MutantServiceTest {

    @MockBean
    private PersonServiceInt personServiceInt;

    @Autowired
    private MutantService mutantService;

    private static Stream<Arguments> personParams() {
        return Stream.of(
                arguments(new String[]{
                        "ATGCGA",
                        "CAGTGC",
                        "TTATGT",
                        "AGAAGG",
                        "CCCCTA",
                        "TCACTG"}, 2, true),
                arguments(new String[]{
                        "ATGCGA",
                        "CAGTGC",
                        "TTATTT",
                        "AGACGG",
                        "GCGTCA",
                        "TCACTG"}, 0, false),
                arguments(new String[]{
                        "ATGCGAA",
                        "CAGTGCT",
                        "TTATGTG",
                        "AGAAGGA",
                        "CCCCTAT",
                        "TCACTGA",
                        "ATGCGAC"}, 2, true),
                arguments(new String[]{
                        "ATGCGAAT",
                        "CAGTGCTG",
                        "TTATGTGC",
                        "AGAAGGAC",
                        "CCCCTATA",
                        "TCACTGAG",
                        "ATGCGACG",
                        "TGGCGACG"}, 2, true),
                arguments(new String[]{
                        "ATGCGA",
                        "CAGTGC",
                        "TTATTT",
                        "AGAAGG",
                        "GCGTCA",
                        "TCACTG"}, 1, false),
                arguments(new String[]{
                        "ATGCGAAT",
                        "TAGGGCTG",
                        "TTATGTGC",
                        "AGTCGGAC",
                        "CCCTTATA",
                        "TCACTGAG",
                        "ATGCGACG",
                        "GGGGGACG"}, 2, true)
        );
    }

    @ParameterizedTest(name = "Repeat {1} - expected {2}")
    @MethodSource("personParams")
    void mutant(String[] dna, int repeat, boolean expected) {
        //Given
        //When
        when(personServiceInt.save(dna, repeat)).thenReturn(new PersonDAO());
        //Then
        boolean response = mutantService.mutant(dna);
        assertThat(response).isEqualTo(expected);
    }
}