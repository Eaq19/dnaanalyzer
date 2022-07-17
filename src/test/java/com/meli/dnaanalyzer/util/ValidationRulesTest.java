package com.meli.dnaanalyzer.util;

import com.meli.dnaanalyzer.exception.ArrayIndexException;
import com.meli.dnaanalyzer.exception.ContentNotAllowedException;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationRulesTest {

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = ValidationRules.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "Constructor is not private");

        constructor.setAccessible(true);
        Assert.assertThrows(InvocationTargetException.class, () -> constructor.newInstance());
    }

    @Test
    public void validateLength() throws ArrayIndexException {
        //Given
        String[] dna = new String[]{
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"};
        //When
        boolean response = ValidationRules.validateLength(dna);
        //Then
        assertThat(response).isTrue();
    }

    @Test(expected = ArrayIndexException.class)
    public void validateLengthException() throws ArrayIndexException {
        //Given
        String[] dna = new String[]{
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA"};
        //When
        ValidationRules.validateLength(dna);
    }

    @Test
    public void validateData() throws ContentNotAllowedException {
        //Given
        String[] dna = new String[]{
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"};
        //When
        boolean response = ValidationRules.validateData(dna);
        //Then
        assertThat(response).isTrue();
    }

    @Test(expected = ContentNotAllowedException.class)
    public void validateDataException() throws ContentNotAllowedException {
        //Given
        String[] dna = new String[]{
                "DTGCGA",
                "CAGTGC",
                "BTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"};
        //When
        ValidationRules.validateData(dna);
    }
}