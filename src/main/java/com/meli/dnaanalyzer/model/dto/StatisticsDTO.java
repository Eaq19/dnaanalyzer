package com.meli.dnaanalyzer.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Parameter that represents the proportion of mutants with respect to the human. " +
            "count_mutant_dna / count_human_dna")
    private Double ratio;
}
