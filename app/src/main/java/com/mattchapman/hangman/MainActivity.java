package com.mattchapman.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    HangmanDBHelper db;
    int versionCode = BuildConfig.VERSION_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView versionTxt = findViewById(R.id.version);
        versionTxt.setText("v"+versionCode);

        db = new HangmanDBHelper(this);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playButtonClick(View view) {
        Intent intent = new Intent(this, HangmanPlay.class);
        startActivity(intent);
    }

    public void aboutMeButtonClick(View view) {
        Uri uriUrl = Uri.parse("https://matt-chap.com/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}