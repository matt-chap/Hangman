package com.mattchapman.hangman;

import android.provider.BaseColumns;

public class HangmanContract {

    private HangmanContract() {
    }

    public static final class HangmanEntry implements BaseColumns {
        public static final String TABLE_NAME = "HangmanWords";
        public static final String COLUMN_CATEGORY = "Category";
        public static final String COLUMN_WORD = "Word";
        public static final String COLUMN_WON = "Won";
    }

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "UserData";
        public static final String COLUMN_HINTS = "Hints";
        public static final String COLUMN_LEVEL = "Level";
    }
}
