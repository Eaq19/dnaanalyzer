package com.meli.dnaanalyzer.controller.impl;

import com.meli.dnaanalyzer.controller.MutantController;
import com.meli.dnaanalyzer.model.Person;
import com.meli.dnaanalyzer.service.MutantServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MutantControllerImpl implements MutantController {

    @Autowired
    private MutantServiceInt mutantServiceInt;

    @Override
    public ResponseEntity<Boolean> mutant(Person person) {
        return ResponseEntity.ok(mutantServiceInt.mutant(person.getDna()));
    }

}
