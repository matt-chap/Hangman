package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playButtonClick(View view){
        Intent intent = new Intent(this, HangmanPlay.class);
        startActivity(intent);
    }

    public void donateButtonClick(View view){
        Uri uriUrl = Uri.parse("https://matt-chap.com/donate/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}