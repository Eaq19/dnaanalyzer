package com.meli.dnaanalyzer.util;

import com.meli.dnaanalyzer.exception.ArrayIndexException;
import com.meli.dnaanalyzer.exception.ContentNotAllowedException;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationRules {

    private ValidationRules() {
        throw new IllegalStateException("ValidationRules class");
    }

    private static final List<String> ALLOWED_LETTERS = Arrays.asList("A", "T", "C", "G");

    public static boolean validateLength(String[] dna) throws ArrayIndexException {
        int length = dna.length;
        boolean response = Arrays.stream(dna).anyMatch(s -> s.length() != length);
        if (response) {
            throw new ArrayIndexException("The array does not have a valid length");
        }
        return true;
    }

    public static boolean validateData(String[] dna) throws ContentNotAllowedException {
        List<ObjectError> contentNotAllowedErrors = getContentErrorsFrom(dna);

        if (!contentNotAllowedErrors.isEmpty()) {
            throw ContentNotAllowedException.createWith(contentNotAllowedErrors);
        }
        return true;
    }


    private static List<ObjectError> getContentErrorsFrom(String[] dna) {

        return Arrays.stream(dna).anyMatch(s -> {
            boolean response = false;
            for (int i = 0; i < s.length(); i++) {
                if (ALLOWED_LETTERS.indexOf(String.valueOf(s.charAt(i))) == -1) {
                    response = true;
                    break;
                }
            }
            return response;
        }) ? Arrays.asList(new ObjectError("Data", "is not appropriate")) : new ArrayList<>();
    }
}
