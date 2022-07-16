package com.meli.dnaanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class DNAAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DNAAnalyzerApplication.class, args);
	}

}
