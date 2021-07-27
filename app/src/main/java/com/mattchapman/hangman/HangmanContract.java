package com.mattchapman.hangman;

import android.provider.BaseColumns;

public class HangmanContract {

    private HangmanContract() {
    }

    public static final class HangmanEntry implements BaseColumns {
        public static final String TABLE_HANGMAN_WORDS = "HangmanWords";
        public static final String COLUMN_CATEGORY = "Category";
        public static final String COLUMN_WORD = "Word";
        public static final String COLUMN_WON = "Won";
    }

    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_USER_DATA = "UserData";
        public static final String COLUMN_HINTS_TAKEN = "HintsTaken";
        public static final String COLUMN_HINT_POINTS = "HintPoints";
        public static final String COLUMN_LEVEL = "Level";
    }
}
