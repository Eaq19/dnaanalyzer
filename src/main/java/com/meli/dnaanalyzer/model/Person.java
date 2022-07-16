package com.meli.dnaanalyzer.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    private String name;
    private String[] dna;
}
