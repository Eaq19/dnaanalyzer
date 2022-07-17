package com.meli.dnaanalyzer.service.impl;

import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.repository.PersonRepository;
import com.meli.dnaanalyzer.service.PersonServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements PersonServiceInt {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public StatisticsDTO stats() {
        StatisticsDTO statisticsDTO = StatisticsDTO.builder().countHumanDna(personRepository.getCountOfPersonByType(1)).countMutantDna(personRepository.getCountOfPersonByType(2)).build();
        if (statisticsDTO.getCountMutantDna() > 0 && statisticsDTO.getCountHumanDna() > 0) {
            statisticsDTO.setRatio(Double.valueOf(statisticsDTO.getCountMutantDna() / statisticsDTO.getCountHumanDna()));
        } else if ((statisticsDTO.getCountMutantDna() == 0 && statisticsDTO.getCountHumanDna() == 0) || statisticsDTO.getCountMutantDna() == 0) {
            statisticsDTO.setRatio(0.0);
        } else if (statisticsDTO.getCountHumanDna() == 0) {
            statisticsDTO.setRatio(null);
        }
        return statisticsDTO;
    }

}
