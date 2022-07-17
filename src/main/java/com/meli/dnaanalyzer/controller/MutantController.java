package com.meli.dnaanalyzer.controller;

import com.meli.dnaanalyzer.exception.ArrayIndexException;
import com.meli.dnaanalyzer.exception.ContentNotAllowedException;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.model.request.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public interface MutantController {

    @PostMapping(value = "/mutant")
    ResponseEntity<Void> mutant(@RequestBody Person person) throws ArrayIndexException, ContentNotAllowedException;

    @GetMapping(value = "/stats")
    ResponseEntity<StatisticsDTO> stats();
}
