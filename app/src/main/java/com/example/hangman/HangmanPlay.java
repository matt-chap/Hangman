package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class HangmanPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_play);
    }

    //TODO: want to pass a the letter to this function so I do not have to write a function for every letter
    public void clickLetter(View view){

    }
}