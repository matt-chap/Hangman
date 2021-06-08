package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;

import com.example.hangman.enums.Category;
import com.example.hangman.model.HangmanCountModel;
import com.example.hangman.model.HangmanWordModel;

import java.text.NumberFormat;


public class HangmanPlay extends AppCompatActivity implements View.OnClickListener {
    TextView txt;
    TextView txtWord;
    String currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_play);

        Button buttonA = findViewById(R.id.buttonA);
        buttonA.setOnClickListener(this);

        Button buttonB = findViewById(R.id.buttonB);
        buttonB.setOnClickListener(this);

        Button buttonC = findViewById(R.id.buttonC);
        buttonC.setOnClickListener(this);

        Button buttonD = findViewById(R.id.buttonD);
        buttonD.setOnClickListener(this);

        Button buttonE = findViewById(R.id.buttonE);
        buttonE.setOnClickListener(this);

        Button buttonF = findViewById(R.id.buttonF);
        buttonF.setOnClickListener(this);

        Button buttonG = findViewById(R.id.buttonG);
        buttonG.setOnClickListener(this);

        Button buttonH = findViewById(R.id.buttonH);
        buttonH.setOnClickListener(this);

        Button buttonI = findViewById(R.id.buttonI);
        buttonI.setOnClickListener(this);

        Button buttonJ = findViewById(R.id.buttonJ);
        buttonJ.setOnClickListener(this);

        Button buttonK = findViewById(R.id.buttonK);
        buttonK.setOnClickListener(this);

        Button buttonL = findViewById(R.id.buttonL);
        buttonL.setOnClickListener(this);

        Button buttonM = findViewById(R.id.buttonM);
        buttonM.setOnClickListener(this);

        Button buttonN = findViewById(R.id.buttonN);
        buttonN.setOnClickListener(this);

        Button buttonO = findViewById(R.id.buttonO);
        buttonO.setOnClickListener(this);

        Button buttonP = findViewById(R.id.buttonP);
        buttonP.setOnClickListener(this);

        Button buttonQ = findViewById(R.id.buttonQ);
        buttonQ.setOnClickListener(this);

        Button buttonR = findViewById(R.id.buttonR);
        buttonR.setOnClickListener(this);

        Button buttonS = findViewById(R.id.buttonS);
        buttonS.setOnClickListener(this);

        Button buttonT = findViewById(R.id.buttonT);
        buttonT.setOnClickListener(this);

        Button buttonU = findViewById(R.id.buttonU);
        buttonU.setOnClickListener(this);

        Button buttonV = findViewById(R.id.buttonV);
        buttonV.setOnClickListener(this);

        Button buttonW = findViewById(R.id.buttonW);
        buttonW.setOnClickListener(this);

        Button buttonX = findViewById(R.id.buttonX);
        buttonX.setOnClickListener(this);

        Button buttonY = findViewById(R.id.buttonY);
        buttonY.setOnClickListener(this);

        Button buttonZ = findViewById(R.id.buttonZ);
        buttonZ.setOnClickListener(this);

        //final TextView
        txtWord = (TextView) findViewById(R.id.text_word);
        //txtWord.setText(currentWord);
        PlayGame();
    }

    public void PlayGame(){
        ResetVars();
        SetNewWordData();
        SetUnderscores();
    }

    public void ResetVars(){
        currentWord = "";
    }

    public void SetUnderscores(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < currentWord.length(); i++)
        {
            char c = currentWord.charAt(i);
            if(Character.isLetter(c)){
                sb.append("_");
            }
            else{
                sb.append(c);
            }
        }
        txtWord.setText(sb.toString());
    }

    public void SetNewWordData(){
        HangmanDBHelper db = new HangmanDBHelper(HangmanPlay.this);
        HangmanWordModel wordData = db.getUnplayedWord();
        currentWord = wordData.getWord();

        SetWordCategory(wordData.getCategory());
        SetWordStats(db);
    }

    public void SetWordCategory(int categoryInt){
        final TextView txtCategory = (TextView) findViewById(R.id.text_category);
        Category cat = Category.getCategoryName(categoryInt);
        txtCategory.setText(cat.name());
    }

    public void SetWordStats(HangmanDBHelper db){
        HangmanCountModel countData = db.getWordCounts();

        final TextView txtUnplayedCount = (TextView) findViewById(R.id.CountUnplayed);
        txtUnplayedCount.setText(NumberFormat.getIntegerInstance().format(countData.getUnplayed()));

        final TextView txtWonCount = (TextView) findViewById(R.id.CountWon);
        txtWonCount.setText(NumberFormat.getIntegerInstance().format(countData.getWon()));

        final TextView txtLossCount = (TextView) findViewById(R.id.CountLoss);
        txtLossCount.setText(NumberFormat.getIntegerInstance().format(countData.getLoss()));
    }

    public void CheckLetterInWord(Character letter){
        StringBuilder sb = new StringBuilder();
        String underscoreWord = txtWord.getText().toString();
        for(int i = 0; i < currentWord.length(); i++)
        {
            char uc = underscoreWord.charAt(i);
            char cc = currentWord.charAt(i);
            if(uc == '_' && Character.isLetter(cc)){
                if(cc == letter){
                    sb.append(letter);
                }
                else{
                    sb.append("_");
                }
            }
            else{
                sb.append(cc);
            }
        }
        txtWord.setText(sb.toString());
    }

    public void setButtonState(View v){
        v.setEnabled(false);
        ((Button)v).setTextColor(Color.parseColor("#d3d3d3"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonA: {
                setButtonState(v);
                CheckLetterInWord('A');
                break;
            }
            case R.id.buttonB: {
                setButtonState(v);
                CheckLetterInWord('B');
                break;
            }
            case R.id.buttonC: {
                setButtonState(v);
                CheckLetterInWord('C');
                break;
            }
            case R.id.buttonD: {
                setButtonState(v);
                CheckLetterInWord('D');
                break;
            }
            case R.id.buttonE: {
                setButtonState(v);
                CheckLetterInWord('E');
                break;
            }
            case R.id.buttonF: {
                setButtonState(v);
                CheckLetterInWord('F');
                break;
            }
            case R.id.buttonG: {
                setButtonState(v);
                CheckLetterInWord('G');
                break;
            }
            case R.id.buttonH: {
                setButtonState(v);
                CheckLetterInWord('H');
                break;
            }
            case R.id.buttonI: {
                setButtonState(v);
                CheckLetterInWord('I');
                break;
            }
            case R.id.buttonJ: {
                setButtonState(v);
                CheckLetterInWord('J');
                break;
            }
            case R.id.buttonK: {
                setButtonState(v);
                CheckLetterInWord('K');
                break;
            }
            case R.id.buttonL: {
                setButtonState(v);
                CheckLetterInWord('L');
                break;
            }
            case R.id.buttonM: {
                setButtonState(v);
                CheckLetterInWord('M');
                break;
            }
            case R.id.buttonN: {
                setButtonState(v);
                CheckLetterInWord('N');
                break;
            }
            case R.id.buttonO: {
                setButtonState(v);
                CheckLetterInWord('O');
                break;
            }
            case R.id.buttonP: {
                setButtonState(v);
                CheckLetterInWord('P');
                break;
            }
            case R.id.buttonQ: {
                setButtonState(v);
                CheckLetterInWord('Q');
                break;
            }
            case R.id.buttonR: {
                setButtonState(v);
                CheckLetterInWord('R');
                break;
            }
            case R.id.buttonS: {
                setButtonState(v);
                CheckLetterInWord('S');
                break;
            }
            case R.id.buttonT: {
                setButtonState(v);
                CheckLetterInWord('T');
                break;
            }
            case R.id.buttonU: {
                setButtonState(v);
                CheckLetterInWord('U');
                break;
            }
            case R.id.buttonV: {
                setButtonState(v);
                CheckLetterInWord('V');
                break;
            }
            case R.id.buttonW: {
                setButtonState(v);
                CheckLetterInWord('W');
                break;
            }
            case R.id.buttonX: {
                setButtonState(v);
                CheckLetterInWord('X');
                break;
            }
            case R.id.buttonY: {
                setButtonState(v);
                CheckLetterInWord('Y');
                break;
            }
            case R.id.buttonZ: {
                setButtonState(v);
                CheckLetterInWord('Z');
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}