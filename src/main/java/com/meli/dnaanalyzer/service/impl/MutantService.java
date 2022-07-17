package com.meli.dnaanalyzer.service.impl;

import com.meli.dnaanalyzer.service.MutantServiceInt;
import com.meli.dnaanalyzer.service.PersonServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.meli.dnaanalyzer.util.Constant.MINIMUM_VALID;
import static com.meli.dnaanalyzer.util.Constant.SEQUENCE_SIZE;

@Service
public class MutantService implements MutantServiceInt {

    private int repeatedSequences = 0;

    @Autowired
    private PersonServiceInt personServiceInt;

    @Override
    public boolean mutant(String[] dna) {
        repeatedSequences = 0;
        int rowLength = dna.length;
        if (rowLength >= SEQUENCE_SIZE) {
            Map<String, Integer> hashAcum = new HashMap<>();
            hashAcum.put("2", 0);
            for (int i = 0; i < dna.length; i++) {
                if (repeatedSequences < MINIMUM_VALID) {
                    traverseArray(i, dna, hashAcum);
                } else {
                    break;
                }
            }
        }
        personServiceInt.save(dna, repeatedSequences);
        return repeatedSequences >= MINIMUM_VALID;
    }

    private void traverseArray(int i, String[] dna, Map<String, Integer> hashAcum) {
        hashAcum.put("0", 0);
        hashAcum.put("1", 0);
        for (int j = 0; j < dna.length; j++) {
            //Validación horizontal
            basicValidation(hashAcum, "0", dna, i, 1);
            //Validación vertical
            basicValidation(hashAcum, "1", dna, i, 2);

            //Validación oblicua central
            if (i == 0) {
                basicValidation(hashAcum, "2", dna, hashAcum.get("2"), 5);
            }
            if (j > 0) {
                obliqueValidation(hashAcum, dna, i, j + i, i + j - 1, false);
            }
            if (j <= i) {
                obliqueValidation(hashAcum, dna, i + 1, j, i + j - 1, true);
            }
            if (repeatedSequences >= MINIMUM_VALID || validateAllSize(hashAcum, dna.length)) {
                break;
            }
        }
    }

    private boolean validateSize(int aux, int length) {
        return ((aux + SEQUENCE_SIZE) < length);
    }

    private boolean validateAllSize(Map<String, Integer> hashAcum, int length) {
        return hashAcum.values().stream().allMatch(aux -> !validateSize(aux - 1, length));
    }

    private void basicValidation(Map<String, Integer> hashAcum, String key, String[] dna, int i, int rule) {
        if (repeatedSequences < MINIMUM_VALID && validateSize(hashAcum.get(key) - 1, dna.length)) {
            sumRepeatedSequence(hashAcum, key, validateRule(i, hashAcum.get(key), dna, rule));
        }
    }

    private void obliqueValidation(Map<String, Integer> hashAcum, String[] dna, int i, int j, int max, boolean left) {
        if (repeatedSequences < MINIMUM_VALID && validateSize(max, dna.length)) {
            String key = calculateKey(i, j);
            hashAcum.computeIfAbsent(key, k -> 0);
            if (left) {
                int jCalculate = (Integer.valueOf(key.substring(0, String.valueOf(i + 1).length())) + hashAcum.get(key));
                if (validateSize(hashAcum.get(key), dna.length) && validateSize(jCalculate - 1, dna.length)) {
                    sumRepeatedSequence(hashAcum, key, validateRule(jCalculate, hashAcum.get(key), dna, 3));
                }
            } else {
                int jCalculate = (Integer.valueOf(key.substring(String.valueOf(i).length())) + hashAcum.get(key));
                if (validateSize(hashAcum.get(key), dna.length) && validateSize(jCalculate - 1, dna.length)) {
                    sumRepeatedSequence(hashAcum, key, validateRule(hashAcum.get(key), jCalculate, dna, 3));
                }
            }
        }
    }

    private String calculateKey(int i, int j) {
        String key = "00";
        if (!(i == j && i == 0)) {
            if (i == 0) {
                key = i + "" + j;
            } else {
                int keyInt = Integer.parseInt(i + "" + j);
                //Hacia la izquierda del vertice oblicuo central
                if (Integer.parseInt(i + "" + i) > keyInt) {
                    key = (i - j) + "0";
                } else {//Hacia la derecha del vertice oblicuo central
                    key = "0" + (j - i);
                }
            }
        }
        return key.equals("0") ? "00" : key;
    }

    private void sumRepeatedSequence(Map<String, Integer> hashAcum, String position, boolean valid) {
        if (valid) {
            hashAcum.put(position, hashAcum.get(position) + SEQUENCE_SIZE - 1);
            repeatedSequences++;
        }
        hashAcum.put(position, hashAcum.get(position) + 1);
    }

    private boolean validateSequence(char charAux) {
        return !(charAux == '\0' || charAux == ' ');
    }

    private char equalsChar(int i, int j, char charAux, String[] dna) {
        try {
            if (charAux == ' ') {
                charAux = dna[i].charAt(j);
            } else {
                if (charAux != dna[i].charAt(j)) charAux = '\0';
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            charAux = '\0';
        }
        return charAux;
    }

    /**
     * Rules
     * 1 - Row initial: j Order: i _ k
     * 2 - Column initial: j Order: k _ i
     * 3 - Oblique Column and Row initial: 0 Order: (i + k) _ (i + k)
     * 5 - Oblique Center initial: i Order: k _ k
     */
    private boolean validateRule(int i, int j, String[] dna, int rule) {
        char charAux = ' ';
        int initial = 0;
        if (rule == 5) {
            initial = i;
        } else if (rule == 3) {
            initial = 0;
        } else {
            initial = j;
        }
        int auxA = 0;
        int auxB = 0;
        for (int k = initial; k < (initial + SEQUENCE_SIZE); k++) {
            switch (rule) {
                case 1: {
                    auxA = i;
                    auxB = k;
                    break;
                }
                case 2: {
                    auxA = k;
                    auxB = i;
                    break;
                }
                case 3: {
                    auxA = i + k;
                    auxB = j + k;
                    break;
                }
                case 5: {
                    auxA = k;
                    auxB = k;
                    break;
                }
                default: {
                    //Error
                }
            }
            charAux = equalsChar(auxA, auxB, charAux, dna);
            if (charAux == '\0') break;
        }
        return validateSequence(charAux);
    }


}
