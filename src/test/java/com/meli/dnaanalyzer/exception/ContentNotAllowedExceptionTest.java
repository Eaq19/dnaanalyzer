package com.meli.dnaanalyzer.exception;

import org.junit.Test;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentNotAllowedExceptionTest {

    @Test
    public void createWith() {
        List<ObjectError> errors = Arrays.asList(new ObjectError("Error", "object Error"));
        ContentNotAllowedException.createWith(errors);
        assertThat(errors).isNotNull();
    }

}