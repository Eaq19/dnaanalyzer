package com.meli.dnaanalyzer.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    private String[] dna;
}
