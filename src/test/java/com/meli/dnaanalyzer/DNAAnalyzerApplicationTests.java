package com.meli.dnaanalyzer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;

class DNAAnalyzerApplicationTests {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Test
    void contextLoads() {
        DNAAnalyzerApplication.main(new String[]{});
        assertThat(activeProfile).isNull();
    }

}
