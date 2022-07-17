package com.meli.dnaanalyzer.util;

import com.meli.dnaanalyzer.exception.ArrayIndexException;
import com.meli.dnaanalyzer.exception.ContentNotAllowedException;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationRules {

    private ValidationRules() {
        throw new IllegalStateException("ValidationRules class");
    }

    private static final List<String> ALLOWED_LETTERS = Arrays.asList("A", "T", "C", "G");

    public static boolean validateLength(String[] dna) throws ArrayIndexException {
        int length = dna.length;
        String response = Arrays.stream(dna).filter(s -> s.length() != length).findFirst().orElse(null);
        if (!(length > 0 && response == null)) {
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
        return Arrays.stream(dna).filter(s -> {
            boolean response = false;
            for (int i = 0; i < s.length(); i++) {
                String letter = String.valueOf(s.charAt(i));
                int index = ALLOWED_LETTERS.indexOf(letter);
                if (index == -1) {
                    response = true;
                    break;
                }
            }
            return response;
        }).map(notAllowedWord -> new ObjectError(notAllowedWord, "is not appropriate")).collect(Collectors.toList());
    }
}
