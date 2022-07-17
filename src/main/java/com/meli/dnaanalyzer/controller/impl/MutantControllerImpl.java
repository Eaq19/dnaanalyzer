package com.meli.dnaanalyzer.controller.impl;

import com.meli.dnaanalyzer.controller.MutantController;
import com.meli.dnaanalyzer.exception.ArrayIndexException;
import com.meli.dnaanalyzer.exception.ContentNotAllowedException;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.model.request.Person;
import com.meli.dnaanalyzer.service.MutantServiceInt;
import com.meli.dnaanalyzer.service.PersonServiceInt;
import com.meli.dnaanalyzer.util.ValidationRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MutantControllerImpl implements MutantController {

    @Autowired
    private MutantServiceInt mutantServiceInt;

    @Autowired
    private PersonServiceInt personServiceInt;

    @Override
    public ResponseEntity<Void> mutant(Person person) throws ArrayIndexException, ContentNotAllowedException {
        ValidationRules.validateLength(person.getDna());
        ValidationRules.validateData(person.getDna());
        return mutantServiceInt.mutant(person.getDna()) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<StatisticsDTO> stats() {
        return ResponseEntity.ok(personServiceInt.stats());
    }

}
