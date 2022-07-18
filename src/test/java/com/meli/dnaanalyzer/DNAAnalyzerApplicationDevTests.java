package com.meli.dnaanalyzer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class DNAAnalyzerApplicationDevTests {

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Test
	void contextLoads() {
		assertThat(activeProfile).isNotNull();
	}

}
