package com.meli.dnaanalyzer.service.impl;

import com.google.gson.Gson;
import com.meli.dnaanalyzer.model.dao.PersonDAO;
import com.meli.dnaanalyzer.model.dao.TypeDAO;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.repository.PersonRepository;
import com.meli.dnaanalyzer.util.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    private Gson gson;

    private static Stream<Arguments> saveParams() {
        return Stream.of(
                arguments(new String[]{
                        "ATGCGA",
                        "CAGTGC",
                        "TTATGT",
                        "AGAAGG",
                        "CCCCTA",
                        "TCACTG"}, 2, Type.MUTANT.getId()),
                arguments(new String[]{
                        "ATGCGA",
                        "CAGTGC",
                        "TTATTT",
                        "AGACGG",
                        "GCGTCA",
                        "TCACTG"}, 0, Type.HUMAN.getId())
        );
    }

    @BeforeEach
    public void setup() {
        gson = new Gson();
        ReflectionTestUtils.setField(personService, "gson", gson);
    }

    @ParameterizedTest(name = "Human {0} - Mutant {1}")
    @CsvSource({
            "100, 40, 0.4",
            "0, 40, ",
            "100, 0, 0",
            "0, 0, 0"
    })
    void stats(long returnHuman, long returnMutant, Double response) {
        //Given
        //When
        when(personRepository.getCountOfPersonByType(Type.HUMAN.getId())).thenReturn(returnHuman);
        when(personRepository.getCountOfPersonByType(Type.MUTANT.getId())).thenReturn(returnMutant);
        //Then
        StatisticsDTO statisticsDTO = personService.stats();
        assertThat(statisticsDTO).isNotNull();
        assertThat(statisticsDTO.getCountHumanDna()).isEqualTo(returnHuman);
        assertThat(statisticsDTO.getCountMutantDna()).isEqualTo(returnMutant);
        assertThat(statisticsDTO.getRatio()).isEqualTo(response);
    }

    @ParameterizedTest(name = "Repeat {1} - TypeId {2}")
    @MethodSource("saveParams")
    void save(String[] dna, int repeat, long typeId) {
        //Given
        String dnaString = gson.toJson(dna);
        TypeDAO typeDAO = new TypeDAO();
        typeDAO.setId(typeId);
        PersonDAO personDAO = new PersonDAO();
        personDAO.setDna(dnaString);
        personDAO.setType(typeDAO);
        PersonDAO personResponseDAO = PersonDAO.builder().id(1L).dna(dnaString).type(typeDAO).build();
        //When
        when(personRepository.save(Mockito.any(PersonDAO.class))).thenReturn(personResponseDAO);
        //Then
        PersonDAO response = personService.save(dna, repeat);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getDna()).isEqualTo(dnaString);
        assertThat(response.getType()).isNotNull();
        assertThat(response.getType().getId()).isEqualTo(typeId);
    }
}