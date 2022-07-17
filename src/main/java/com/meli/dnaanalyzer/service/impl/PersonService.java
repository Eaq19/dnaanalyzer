package com.meli.dnaanalyzer.service.impl;

import com.google.gson.Gson;
import com.meli.dnaanalyzer.model.dao.PersonDAO;
import com.meli.dnaanalyzer.model.dao.TypeDAO;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.repository.PersonRepository;
import com.meli.dnaanalyzer.service.PersonServiceInt;
import com.meli.dnaanalyzer.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.meli.dnaanalyzer.util.Constant.MINIMUM_VALID;

@Service
public class PersonService implements PersonServiceInt {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private Gson gson;

    @Override
    public StatisticsDTO stats() {
        StatisticsDTO statisticsDTO = StatisticsDTO.builder().countHumanDna(personRepository.getCountOfPersonByType(1)).countMutantDna(personRepository.getCountOfPersonByType(2)).build();
        if (statisticsDTO.getCountMutantDna() > 0 && statisticsDTO.getCountHumanDna() > 0) {
            statisticsDTO.setRatio((double) statisticsDTO.getCountMutantDna() / (double) statisticsDTO.getCountHumanDna());
        } else if (statisticsDTO.getCountMutantDna() == 0) {
            statisticsDTO.setRatio(0.0);
        } else if (statisticsDTO.getCountHumanDna() == 0) {
            statisticsDTO.setRatio(null);
        }
        return statisticsDTO;
    }

    @Override
    public PersonDAO save(String[] dna, int repeat) {
        PersonDAO personDAO = PersonDAO.builder().dna(gson.toJson(dna)).build();
        if (repeat >= MINIMUM_VALID) {
            personDAO.setType(TypeDAO.builder().id(Type.MUTANT.getId()).build());
        } else {
            personDAO.setType(TypeDAO.builder().id(Type.HUMAN.getId()).build());
        }
        return personRepository.save(personDAO);
    }

}
