package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HangmanPlay extends AppCompatActivity implements View.OnClickListener {
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_play);

        txt = findViewById(R.id.textView2);

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
    }

    //TODO: want to pass a the letter to this function so I do not have to write a function for every letter
    public void clickLetter(View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonA: {
                txt.append("A");
                break;
            }
            case R.id.buttonB: {
                txt.append("B");
                break;
            }
            case R.id.buttonC: {
                txt.append("C");
                break;
            }
            case R.id.buttonD: {
                txt.append("D");
                break;
            }
            case R.id.buttonE: {
                txt.append("E");
                break;
            }
            case R.id.buttonF: {
                txt.append("F");
                break;
            }
            case R.id.buttonG: {
                txt.append("G");
                break;
            }
            case R.id.buttonH: {
                txt.append("H");
                break;
            }
            case R.id.buttonI: {
                txt.append("I");
                break;
            }
            case R.id.buttonJ: {
                txt.append("J");
                break;
            }
            case R.id.buttonK: {
                txt.append("K");
                break;
            }
            case R.id.buttonL: {
                txt.append("L");
                break;
            }
            case R.id.buttonM: {
                txt.append("M");
                break;
            }
            case R.id.buttonN: {
                txt.append("N");
                break;
            }
            case R.id.buttonO: {
                txt.append("O");
                break;
            }
            case R.id.buttonP: {
                txt.append("P");
                break;
            }
            case R.id.buttonQ: {
                txt.append("Q");
                break;
            }
            case R.id.buttonR: {
                txt.append("R");
                break;
            }
            case R.id.buttonS: {
                txt.append("S");
                break;
            }
            case R.id.buttonT: {
                txt.append("T");
                break;
            }
            case R.id.buttonU: {
                txt.append("U");
                break;
            }
            case R.id.buttonV: {
                txt.append("V");
                break;
            }
            case R.id.buttonW: {
                txt.append("W");
                break;
            }
            case R.id.buttonX: {
                txt.append("X");
                break;
            }
            case R.id.buttonY: {
                txt.append("Y");
                break;
            }
            case R.id.buttonZ: {
                txt.append("Z");
                break;
            }
        }
    }
}