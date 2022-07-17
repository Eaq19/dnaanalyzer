package com.meli.dnaanalyzer.service;

import com.meli.dnaanalyzer.model.dao.PersonDAO;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;

public interface PersonServiceInt {

    /**
     * Método que se encarga de obtener las estadísticas de personas analizadas por el Api.
     * <p>
     * Method that is responsible for obtaining the statistics of people analyzed by the API.
     *
     * @return StatisticsDTO Object that represents the statistics
     */
    StatisticsDTO stats();

    /**
     * Método que se encarga de persistir la información de una persona asignándole el tipo al que pertenece
     * (Humano o mutante).
     * <p>
     * Method that is responsible for persisting the information of a person by assigning the type to which it belongs
     * (Human or mutant).
     *
     * @param dna    Array of Strings that represents the DNA of a person, it must be a matrix [NxN], in which N must be
     *               the number of letters that each position of the array will contain.
     * @param repeat Number of repeated sequences found
     * @return PersonDAO that represents the with id
     */
    PersonDAO save(String[] dna, int repeat);
}
