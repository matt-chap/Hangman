package com.example.hangman.enums;

public enum Category {
    UNKNOWN(0),
    GEOGRAPHY(1),
    MOVIES(2),
    MUSIC(3),
    ANIMALS(4);

    private final int codeIndex;

    private Category(int code) {
        this.codeIndex = code;
    }

    public int toInt() {
        return codeIndex;
    }

    public String toString() {
        //only override toString, if the returned value has a meaning for the
        //human viewing this value
        return String.valueOf(codeIndex);
    }

    //private int legIndex;

    //private LegNo(int legIndex) { this.legIndex = legIndex; }

    public static Category getLeg(int legIndex) {
        for (Category l : Category.values()) {
            if (l.codeIndex == legIndex) return l;
        }
        throw new IllegalArgumentException("Leg not found. Amputated?");
    }
}
