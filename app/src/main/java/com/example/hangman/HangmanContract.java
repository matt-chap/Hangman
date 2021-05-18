package com.example.hangman;

import android.provider.BaseColumns;

public class HangmanContract {

    private HangmanContract() {}

    public static final class HangmanEntry implements BaseColumns {
        public static final String TABLE_NAME = "HangmanWords";
        public static final String COLUMN_CATEGORY = "Category";
        public static final String COLUMN_WORD = "Word";
        public static final String COLUMN_WON = "Won";
    }
}
