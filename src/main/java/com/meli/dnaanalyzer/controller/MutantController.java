package com.meli.dnaanalyzer.controller;

import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.model.request.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public interface MutantController {

    @PostMapping(value = "/mutant")
    ResponseEntity<Boolean> mutant(@RequestBody Person person);

    @GetMapping(value = "/stats")
    ResponseEntity<StatisticsDTO> stats();
}
