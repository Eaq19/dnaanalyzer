package com.meli.dnaanalyzer.controller.impl;

import com.meli.dnaanalyzer.controller.MutantController;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.model.request.Person;
import com.meli.dnaanalyzer.service.MutantServiceInt;
import com.meli.dnaanalyzer.service.PersonServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MutantControllerImpl implements MutantController {

    @Autowired
    private MutantServiceInt mutantServiceInt;

    @Autowired
    private PersonServiceInt personServiceInt;

    @Override
    public ResponseEntity<Boolean> mutant(Person person) {
        return ResponseEntity.ok(mutantServiceInt.mutant(person.getDna()));
    }

    @Override
    public ResponseEntity<StatisticsDTO> stats() {
        return ResponseEntity.ok(personServiceInt.stats());
    }

}
