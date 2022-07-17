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

    /**
     * Método que se encarga de validar a partir de una secuencia de adn si una persona se puede catalogar como Humano o
     * Mutante, si en la lista de secuencias se encuentran dos o mas coincidencias de cuatro letras seguidas de forma
     * horizontal, vertical u oblicua se considera mutante de lo contrario se considera humano, y con esta validación
     * se persiste en base de datos.
     * <p>
     * Method that is responsible for validating from a DNA sequence if a person can be classified as Human or Mutant,
     * if in the list of sequences there are two or more matches of four letters followed horizontally, vertically or
     * obliquely, it is considered mutant otherwise it is considered human, and with this validation it is persisted
     * in the database.
     *
     * @param dna Array of Strings that represents the DNA of a person, it must be a matrix [NxN], in which N must be
     *            the number of letters that each position of the array will contain.
     * @return a boolean variable that represents true if he is a mutant and false if he is a human.
     */
    @Override
    public boolean mutant(String[] dna) {
        repeatedSequences = 0;
        //Se crea una colección tipo map que permita mantener la cuenta de las posiciones que ya fueron evaluadas.
        Map<String, Integer> hashAcum = new HashMap<>();
        hashAcum.put("2", 0);
        /**
         * Se recorre el listado inicial a partir de la cantidad de elementos con los que cuente el array,
         * y se valida si ya se cumplió con el mínimo de secuencias repetidas validas para establecer que el ADN
         * pertenece a un mutante, se realiza para evitar recorridos innecesarios
         */
        for (int i = 0; i < dna.length; i++) {
            if (repeatedSequences < MINIMUM_VALID) {
                traverseArray(i, dna, hashAcum);
            } else {
                break;
            }
        }
        /**
         * Se llama al servicio de persona para persistir la secuencia de ADN ingresada.
         */
        personServiceInt.save(dna, repeatedSequences);
        return repeatedSequences >= MINIMUM_VALID;
    }

    /**
     * Función que va recorrer todos los nivel de la matriz a partir del ciclo padre. Y que invoca las diferentes
     * funciones que emplearan la logica de validación de secuencias por las formas:
     * - Horizontal en posiciones corresponde a 00, 01, 02, 03
     * - Vertical en posiciones corresponde a 00, 10, 20, 30
     * - Oblicua Central en posiciones corresponde a 00, 11, 22
     * - Oblicua Derecha representa las coincidencias en diagonal que quedan a la derecha de la oblicua central.
     * En posiciones corresponde a 01, 12, 23, 34
     * - Oblicua Izquierda representa las coincidencias en diagonal que quedan a la izquierda de la oblicua central.
     * En posiciones corresponde a 10, 21, 32, 43
     *
     * @param i Posición correspecto al ciclo inicial y que representa un eje de lectura de la matriz base
     * @param dna matriz con adn
     * @param hashAcum Colección auxiliar
     */
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
            //Validación oblicua derecha, se toma todos los que son mayores a j, esto para omitir las demas validaciones
            if (j > 0) {
                obliqueValidation(hashAcum, dna, i, j + i, i + j - 1, false);
            }
            //Validación oblicua izquierda, se toma los que son menores o iguales a j
            if (j <= i) {
                obliqueValidation(hashAcum, dna, i + 1, j, i + j - 1, true);
            }
            // Se valida si los tamaños son validos y si no para continuar con la siguiente iteración para evitar
            // trabajo innecesario
            if (repeatedSequences >= MINIMUM_VALID || validateAllSize(hashAcum, dna.length)) {
                break;
            }
        }
    }

    /**
     * Función que valida si la posición que se va a evaluar tiene datos validos, esto para evitar procesamiento
     * innecesario y para evitar excepciones por no encontrar la posición dentro del array
     *
     * @param aux Posición a evaluar
     * @param length Posiciones validas
     * @return
     */
    private boolean validateSize(int aux, int length) {
        return ((aux + SEQUENCE_SIZE) < length);
    }

    /**
     * Función que valida si los elementos de la colección map se mantienen entre los rangos de tamaño del array inicial.
     *
     * @param hashAcum Colección auxiliar
     * @param length Posiciones validas
     * @return
     */
    private boolean validateAllSize(Map<String, Integer> hashAcum, int length) {
        return hashAcum.values().stream().allMatch(aux -> !validateSize(aux - 1, length));
    }

    /**
     * Función que orquesta el llamado de las funciones basicas de validación de secuencia y suma de los respectivos
     * contadores dentro de la colección map.
     *
     * Las validaciones que maneja son:
     *  - Horizontal.
     *  - Vertical.
     *  - Oblicua central.
     *
     * @param hashAcum Colección auxiliar
     * @param key Clave de la validación en la colección auxiliar
     * @param dna lista a validar
     * @param i Posición correspecto al ciclo inicial y que representa un eje de lectura de la matriz base
     * @param rule Numero que representa que validación se va a trabajar.
     */
    private void basicValidation(Map<String, Integer> hashAcum, String key, String[] dna, int i, int rule) {
        if (repeatedSequences < MINIMUM_VALID && validateSize(hashAcum.get(key) - 1, dna.length)) {
            sumRepeatedSequence(hashAcum, key, validateRule(i, hashAcum.get(key), dna, rule));
        }
    }

    /**
     * Función que orquesta el llamado de las funciones oblicuas de validación de secuencia tanto la validación hacia
     * el lado derecho de la matriz como hacia el lado izquierdo y suma respectiva de contadores dentro de la colección
     * map.
     *
     * @param hashAcum Colección auxiliar
     * @param dna lista a validar
     * @param i Posición correspecto al ciclo inicial y que representa un eje de lectura de la matriz base
     * @param j Posición correspecto al ciclo secundario y que representa un eje de lectura de la matriz base
     * @param max Posición maxima que se va a evaluar para validar su tamaño
     * @param left Variable auxiliar que representa cual es el tipo de validación a aplicar
     */
    private void obliqueValidation(Map<String, Integer> hashAcum, String[] dna, int i, int j, int max, boolean left) {
        if (repeatedSequences < MINIMUM_VALID && validateSize(max, dna.length)) {
            String key = calculateKey(i, j);
            hashAcum.computeIfAbsent(key, k -> 0);
            if (left) {
                //Se calcula la posición inicial de la validación
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

    /**
     * Función que permite generar una key base para las validaciones oblicuas y asi poder almacenar de forma temporal
     * las posiciones que ya se evaluarón para no volverlas a tener en cuenta.
     *
     * @param i Posición correspecto al ciclo inicial y que representa un eje de lectura de la matriz base
     * @param j Posición correspecto al ciclo secundario y que representa un eje de lectura de la matriz base
     * @return String Responde una clave tipo texto para colección map
     */
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

    /**
     * Función que suma uno o cuatro unidades a la colección auxiliar que mantiene la cuenta. Se suma solo una unidad
     * cuando el valor validado no corresponde a una secuencia valida, si el valor analizado corresponde a una secuencia
     * de cuatro letras repetidas se suma una unidad a la variable de repeticiones general y se suman cuatro unidades a
     * la cuenta esto para omitir las otras tres letras en la siguiente validación del mismo tipo.
     *
     * @param hashAcum Colección auxiliar
     * @param position key dentro de la colección
     * @param valid Variable auxiliar que representa si fue una secuencia valida.
     */
    private void sumRepeatedSequence(Map<String, Integer> hashAcum, String position, boolean valid) {
        if (valid) {
            hashAcum.put(position, hashAcum.get(position) + SEQUENCE_SIZE - 1);
            repeatedSequences++;
        }
        hashAcum.put(position, hashAcum.get(position) + 1);
    }

    /**
     * Función que se encarga de comparar los caracteres de la secuencia segun su posición.
     *
     * @param i Posición correspecto al ciclo inicial y que representa un eje de lectura de la matriz base
     * @param j Posición correspecto al ciclo secundario y que representa un eje de lectura de la matriz base
     * @param charAux Variable auxiliar que almacena el caracter a comparar
     * @param dna array que representa matriz de adn
     * @return
     */
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
     * Funciones que maneja las reglas de validación a partir de las reglas establecidas.
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
        return !(charAux == '\0' || charAux == ' ');
    }

}
