package com.example.hangman;

public enum ColumnCategory {
    UNKNOWN(0),
    GEOGRAPHY(1),
    MOVIES(2),
    MUSIC(3),
    ANIMALS(4);

    private final int code;

    private ColumnCategory(int code) {
        this.code = code;
    }

    public int toInt() {
        return code;
    }

    public String toString() {
        //only override toString, if the returned value has a meaning for the
        //human viewing this value
        return String.valueOf(code);
    }
}
