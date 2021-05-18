package com.example.hangman;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.hangman.HangmanContract.*;

import androidx.annotation.Nullable;

public class HangmanDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "hangman.db";
    public static final int DATABASE_VERSION = 1;

    public HangmanDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_HANGMAN_TABLE = "CREATE TABLE " +
                HangmanEntry.TABLE_NAME + " (" +
                HangmanEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HangmanEntry.COLUMN_CATEGORY + " INTEGER DEFAULT 0, " +
                HangmanEntry.COLUMN_WORD + " TEXT NOT NULL, " +
                HangmanEntry.COLUMN_WON + " INTEGER DEFAULT 0" +
                ");";

        db.execSQL(SQL_CREATE_HANGMAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
