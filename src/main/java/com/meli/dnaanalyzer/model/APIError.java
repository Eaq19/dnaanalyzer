package com.meli.dnaanalyzer.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class APIError {

    private List<String> errors;
}
