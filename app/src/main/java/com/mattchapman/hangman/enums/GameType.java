package com.mattchapman.hangman.enums;

public enum GameType {
    NEW_GAME(0),
    LOST_GAME(1),
    WON_GAME(2);

    private final int code;

    private GameType(int code) {
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
