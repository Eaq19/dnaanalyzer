package com.meli.dnaanalyzer.exception;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayIndexExceptionTest {

    @Test
    public void testConstructor() {
        ArrayIndexException exception = new ArrayIndexException("Error");
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Error");
    }
}