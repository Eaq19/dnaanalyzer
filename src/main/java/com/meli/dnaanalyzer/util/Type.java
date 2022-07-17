package com.meli.dnaanalyzer.util;

public enum Type {

    HUMAN(1),
    MUTANT(2);

    private long id;

    private Type(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
    }
}
