package com.mattchapman.hangman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mattchapman.hangman.HangmanContract.*;
import com.mattchapman.hangman.model.HangmanCountModel;
import com.mattchapman.hangman.model.HangmanWordModel;
import com.mattchapman.hangman.model.UserDataModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HangmanDBHelper extends SQLiteOpenHelper {
    // The Android's default system path
    // of your application database.
    private static String DB_PATH = "";
    public static final String DATABASE_NAME = "hangman.db";
    public static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public HangmanDBHelper(Context myContext) {
        super(myContext, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = myContext;
        DB_PATH = myContext.getDatabasePath(DATABASE_NAME).toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // It is an abstract method
        // but we define our own method here.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // It is an abstract method which is
        // used to perform different task
        // based on the version of database.
    }

    // Creates an empty database
    // on the system and rewrites it
    // with your own database.
    public void createDataBase() {
        boolean dbExist = checkDataBase();

        if (!dbExist) {
            // By calling this method and
            // the empty database will be
            // created into the default system
            // path of your application
            // so we are gonna be able
            // to overwrite that database
            // with our database.
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    // Check if the database already exist
    // to avoid re-copying the file each
    // time you open the application
    // return true if it exists
    // false if it doesn't.
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH;
            checkDB = SQLiteDatabase.openDatabase( myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            Log.e("message", "" + e);
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Copies your database from your
     * local assets-folder to the just
     * created empty database in the
     * system folder, from where it
     * can be accessed and handled.
     * This is done by transferring byte stream.
     */
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the
        // input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() {
        // Open the database
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        // close the database.
        if (myDataBase != null) {
            myDataBase.close();
        }

        super.close();
    }

    //region Word Look Ups
    // This method is used to get a new
    // the word and the corresponding category from the database.
    public HangmanWordModel getUnplayedWord() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            // To return a random word
            String query = "SELECT * FROM " + HangmanEntry.TABLE_HANGMAN_WORDS + " WHERE Won = 0 ORDER BY RANDOM() LIMIT 1;";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int category = cursor.getInt(0);
                String word = cursor.getString(1);
                int won = cursor.getInt(2);
                cursor.close();
                return new HangmanWordModel(word, category, won);
            }
        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.getUnplayedWord");
            fbc.recordException(ex);
            return new HangmanWordModel("987-654-321", 0, 0);
        }

        return new HangmanWordModel("123-456-789", 0, 0);
    }

    public HangmanWordModel getLostWord() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            // To return a random word
            String query = "SELECT * FROM " + HangmanEntry.TABLE_HANGMAN_WORDS + " WHERE Won = 1 ORDER BY RANDOM() LIMIT 1;";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int category = cursor.getInt(0);
                String word = cursor.getString(1);
                int won = cursor.getInt(2);
                cursor.close();
                return new HangmanWordModel(word, category, won);
            }
        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.getLostWord");
            fbc.recordException(ex);
            return new HangmanWordModel("987-654-321", 0, 0);
        }

        // TODO: Perhaps need to throw error or try again
        return new HangmanWordModel("123-456-789", 0, 0);
    }

    public HangmanCountModel getWordCounts() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            // query help us to return all data
            // the present in the ALGO_TOPICS table.
            String query = "SELECT " +
                    "SUM(CASE WHEN Won = 0 THEN 1 ELSE 0 END) as Unplayed," +
                    "SUM(CASE WHEN Won = 1 THEN 1 ELSE 0 END) as Loss," +
                    "SUM(CASE WHEN Won = 2 THEN 1 ELSE 0 END) as Won" +
                    " FROM " + HangmanEntry.TABLE_HANGMAN_WORDS + ";";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int unplayed = cursor.getInt(0);
                int loss = cursor.getInt(1);
                int won = cursor.getInt(2);
                cursor.close();
                return new HangmanCountModel(won, loss, unplayed);
            }

        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.getWordCounts");
            fbc.recordException(ex);
            return new HangmanCountModel(0, 0, 0);
        }

        return new HangmanCountModel(0, 0, 0);
    }

    public void setWordLoss(String word) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // query help us to return all data
            // the present in the ALGO_TOPICS table.
            ContentValues cv = new ContentValues();
            cv.put("Won", 1);

            String[] args = new String[]{word};
            db.update(HangmanEntry.TABLE_HANGMAN_WORDS, cv, "Word=?", args);
        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.setWordLoss");
            fbc.recordException(ex);
        }
    }

    public void setWordWon(String word) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // query help us to return all data
            // the present in the ALGO_TOPICS table.
            ContentValues cv = new ContentValues();
            cv.put("Won", 2);

            String[] args = new String[]{word};
            db.update(HangmanEntry.TABLE_HANGMAN_WORDS, cv, "Word=?", args);
        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.setWordWon");
            fbc.recordException(ex);
        }
    }
    //endregion Word Look Ups

    //region User Data Look Ups
    public UserDataModel getUserData() {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            // query help us to return all data
            // the present in the ALGO_TOPICS table.
            String query = "SELECT *" +
                    " FROM " + UserEntry.TABLE_USER_DATA +
                    " LIMIT 1;";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int hintsTaken = cursor.getInt(0);
                int hintPoints = cursor.getInt(1);
                int level = cursor.getInt(2);
                return new UserDataModel(hintsTaken, hintPoints, level);
            }
            cursor.close();
        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.getUserData");
            fbc.recordException(ex);
            return new UserDataModel(0, 0, 0);
        }

        return new UserDataModel(0, 0, 0);
    }

    public void setUserHintTaken(int hintTakenAmount) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // query help us to return all data
            // the present in the ALGO_TOPICS table.
            ContentValues cv = new ContentValues();
            cv.put(UserEntry.COLUMN_HINTS_TAKEN, hintTakenAmount);

            db.update(UserEntry.TABLE_USER_DATA, cv, null, null);
        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.setUserHintTaken");
            fbc.recordException(ex);
        }
    }

    public void setUserHintPoints(int hintPointAmount) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // query help us to return all data
            // the present in the ALGO_TOPICS table.
            ContentValues cv = new ContentValues();
            cv.put(UserEntry.COLUMN_HINT_POINTS, hintPointAmount);

            db.update(UserEntry.TABLE_USER_DATA, cv, null, null);
        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.setUserHintPoints");
            fbc.recordException(ex);
        }
    }

    public void setUserLevel(int levelAmount) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // query help us to return all data
            // the present in the ALGO_TOPICS table.
            ContentValues cv = new ContentValues();
            cv.put(UserEntry.COLUMN_LEVEL, levelAmount);

            db.update(UserEntry.TABLE_USER_DATA, cv, null, null);
        } catch (Exception ex) {
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanDBHelper.setUserLevel");
            fbc.recordException(ex);
        }
    }
    //endregion User Data Look Ups
}
