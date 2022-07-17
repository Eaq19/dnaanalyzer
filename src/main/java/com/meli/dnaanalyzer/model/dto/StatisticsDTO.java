package com.meli.dnaanalyzer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StatisticsDTO {

    @JsonProperty("count_mutant_dna")
    @SerializedName("count_mutant_dna")
    private long countMutantDna;
    @JsonProperty("count_human_dna")
    @SerializedName("count_human_dna")
    private long countHumanDna;
    private Double ratio;
}
