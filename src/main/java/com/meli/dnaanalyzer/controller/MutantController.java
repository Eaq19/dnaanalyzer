package com.meli.dnaanalyzer.controller;

import com.meli.dnaanalyzer.exception.ArrayIndexException;
import com.meli.dnaanalyzer.exception.ContentNotAllowedException;
import com.meli.dnaanalyzer.model.APIError;
import com.meli.dnaanalyzer.model.dto.StatisticsDTO;
import com.meli.dnaanalyzer.model.request.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public interface MutantController {

    @Operation(summary = "Verify and store in a database if a person is human or mutant from their DNA")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Returns statistics information", content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "400", description = "Input data error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIError.class))}), @ApiResponse(responseCode = "403", description = "The person received is a human", content = @Content), @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIError.class))})})
    @PostMapping(value = "/mutant")
    ResponseEntity<Void> mutant(@Parameter(description = "Object that represents a person with their DNA") @RequestBody Person person) throws ArrayIndexException, ContentNotAllowedException;

    @Operation(summary = "Obtain statistics from the information stored in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Returns statistics information", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StatisticsDTO.class))}), @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = APIError.class))})})
    @GetMapping(value = "/stats")
    ResponseEntity<StatisticsDTO> stats();
}
