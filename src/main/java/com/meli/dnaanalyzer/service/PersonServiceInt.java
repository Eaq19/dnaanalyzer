package com.meli.dnaanalyzer.service;

import com.meli.dnaanalyzer.model.dao.PersonDAO;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;

public interface PersonServiceInt {

    StatisticsDTO stats();

    PersonDAO save(String[] dna, int repeat);
}
