package com.meli.dnaanalyzer.service;

public interface MutantServiceInt {

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
    boolean mutant(String[] dna);
}
