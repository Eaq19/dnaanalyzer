package com.meli.dnaanalyzer.controller;

import com.meli.dnaanalyzer.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public interface MutantController {

    @PostMapping(value = "/mutant")
    ResponseEntity<Boolean> mutant(@RequestBody Person person);
}
