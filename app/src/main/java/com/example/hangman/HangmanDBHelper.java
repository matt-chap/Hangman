package com.example.hangman;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hangman.HangmanContract.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HangmanDBHelper extends SQLiteOpenHelper {

    // The Android's default system path
    // of your application database.
    private static String DB_PATH = "";
    public static final String DATABASE_NAME = "hangman.db";
    public static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;
    private SQLiteOpenHelper sqLiteOpenHelper;

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
    public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        }
        else {
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
            }
            catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    // Check if the database already exist
    // to avoid re-copying the file each
    // time you open the application
    // return true if it exists
    // false if it doesn't.
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH;
            checkDB
                    = SQLiteDatabase
                    .openDatabase(
                            myPath, null,
                            SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e) {

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
     * This is done by transferring bytestream.
     * */
    private void copyDataBase() throws IOException
    {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH;

        // Open the empty db as the output stream
        OutputStream myOutput
                = new FileOutputStream(outFileName);

        // transfer bytes from the
        // inputfile to the outputfile
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

    public void openDataBase() throws SQLException
    {
        // Open the database
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close()
    {
        // close the database.
        if (myDataBase != null){
            myDataBase.close();
        }

        super.close();
    }

    // This method is used to get a new
    // the word and the corresponding category from the database.
    public List<HangmanWordModel> getUnplayedWord()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        List<HangmanWordModel> list = new ArrayList<>();

        // query help us to return all data
        // the present in the ALGO_TOPICS table.
        String query = "SELECT * FROM " + HangmanEntry.TABLE_NAME + " WHERE Won = 0 LIMIT 1;";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int category = cursor.getInt(0);
                String word = cursor.getString(1);
                int won = cursor.getInt(2);

                HangmanWordModel newWord = new HangmanWordModel(word, category, won);
                list.add(newWord);
            } while (cursor.moveToNext());
        } else{
            // Todo: log?
        }
        return list;
    }
}
