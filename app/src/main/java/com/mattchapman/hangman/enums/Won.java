package com.mattchapman.hangman.enums;

public enum Won {
    USER_NOT_PLAYED(0),
    USER_LOST(1),
    USER_WON(2);

    private final int code;

    private Won(int code) {
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
