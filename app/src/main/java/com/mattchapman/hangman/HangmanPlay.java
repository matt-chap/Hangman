package com.mattchapman.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import com.mattchapman.hangman.enums.Category;
import com.mattchapman.hangman.enums.GameType;
import com.mattchapman.hangman.model.HangmanCountModel;
import com.mattchapman.hangman.model.HangmanWordModel;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.NumberFormat;


public class HangmanPlay extends AppCompatActivity implements View.OnClickListener {
    TextView txtWord;
    String currentWord;
    Integer wrongLetterCount;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_play);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if(isTestDevice()) {
            mAdView.setVisibility(View.GONE);
        }

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

        txtWord = (TextView) findViewById(R.id.text_word);
        PlayGame(GameType.NEW_GAME);
    }

    public void PlayGame(GameType gameType) {
        // Prevent user from going further until we decide what point they are
        CardView card = findViewById(R.id.playAgainView);
        card.setVisibility(View.VISIBLE);

        // Only init the DB helper one time
        HangmanDBHelper db = new HangmanDBHelper(HangmanPlay.this);
        HangmanCountModel countData;

        String gameOverTxt = "";
        if (gameType == GameType.NEW_GAME){
            countData = db.getWordCounts();
        }
        else if (gameType == GameType.WON_GAME) {
            db.setWordWon(currentWord);
            gameOverTxt = "You WON!";

            countData = db.getWordCounts();
        }
        else if (gameType == GameType.LOST_GAME) {
            db.setWordLoss(currentWord);
            gameOverTxt = "You LOST." + System.getProperty("line.separator");
            gameOverTxt += "The word was" + System.getProperty("line.separator");
            gameOverTxt += currentWord;

            countData = db.getWordCounts();
        }
        else {
            //Should never hit here
            gameOverTxt = "Unknown play type, please close the app and try again.";
            countData = new HangmanCountModel(0,0,0);
        }

        // Win, Lost and Unknown will have text
        TextView playAgainTxt = findViewById(R.id.playAgainText);
        playAgainTxt.setText(gameOverTxt);

        Button playAgain = findViewById(R.id.playAgainButton);
        TextView playOver = findViewById(R.id.playOver);
        TextView playLossGames = findViewById(R.id.playLossGames);


        // Reset Game
        ResetVars();
        ResetButtonState();
        SetWordStats(countData);



        // Display Game Over text or Just start a new game
        if (countData.getUnplayed() != 0){
            // Can play unplayed so should get that next value
            HangmanWordModel wordData = db.getUnplayedWord();
            currentWord = wordData.getWord();
            SetWordCategory(wordData.getCategory());

            if (gameType == GameType.NEW_GAME){
                card.setVisibility(View.GONE);
            }
            else{
                playOver.setVisibility(View.GONE);
                playLossGames.setVisibility(View.GONE);

                playAgainTxt.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);
                playAgain.setText("Play Again?");
            }
        } else if(countData.getLoss() != 0){
            // Can replay lost games so let the user do that
            playOver.setVisibility(View.VISIBLE);
            playLossGames.setVisibility(View.VISIBLE);

            playAgainTxt.setVisibility(View.VISIBLE);
            playAgain.setVisibility(View.VISIBLE);
            playAgain.setText("Play Lost Words?");

            HangmanWordModel wordData = db.getLostWord();
            currentWord = wordData.getWord();
            SetWordCategory(wordData.getCategory());
        } else {
            // The game is over since unplayed and lost games have been exhausted
            playOver.setVisibility(View.VISIBLE);
            playLossGames.setVisibility(View.GONE);

            playAgainTxt.setVisibility(View.GONE);
            playAgain.setVisibility(View.GONE);
        }

        SetUnderscores();
    }

    public void PlayAgainClick(View view) {
        // Data should already be loaded so all that has to happen is the screen will disappear
        CardView card = findViewById(R.id.playAgainView);
        card.setVisibility(View.GONE);
    }

    public void ResetVars() {
        wrongLetterCount = 0;
        ImageView pic = findViewById(R.id.hangmanPic);
        pic.setBackground(ContextCompat.getDrawable(this, R.drawable.svg_hangman0));
    }

    public void SetUnderscores() {
        if (currentWord != null) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < currentWord.length(); i++) {
                char c = currentWord.charAt(i);
                if (Character.isLetter(c)) {
                    sb.append("_");
                } else {
                    sb.append(c);
                }
            }

            txtWord.setText(sb.toString());
        } else {
            txtWord.setText("You Win");
        }
    }

    public void SetWordCategory(int categoryInt) {
        final TextView txtCategory = (TextView) findViewById(R.id.text_category);
        Category cat = Category.getCategoryName(categoryInt);
        txtCategory.setText(cat.name());
    }

    public void SetWordStats(HangmanCountModel countData) {
        final TextView txtUnplayedCount = findViewById(R.id.CountUnplayed);
        txtUnplayedCount.setText(NumberFormat.getIntegerInstance().format(countData.getUnplayed()));

        final TextView txtWonCount = findViewById(R.id.CountWon);
        txtWonCount.setText(NumberFormat.getIntegerInstance().format(countData.getWon()));

        final TextView txtLossCount = findViewById(R.id.CountLoss);
        txtLossCount.setText(NumberFormat.getIntegerInstance().format(countData.getLoss()));
    }

    public void CheckLetterInWord(Character letter) {
        StringBuilder sb = new StringBuilder();
        String underscoreWord = txtWord.getText().toString();

        if (currentWord != null && currentWord.indexOf(letter) != -1) {
            // Word contains letter
            for (int i = 0; i < currentWord.length(); i++) {
                char uc = underscoreWord.charAt(i);
                char cc = currentWord.charAt(i);
                if (uc == '_' && Character.isLetter(cc)) {
                    if (cc == letter) {
                        sb.append(letter);
                    } else {
                        sb.append("_");
                    }
                } else {
                    sb.append(cc);
                }
            }
            String newTxt = sb.toString();
            txtWord.setText(newTxt);
            if (newTxt.indexOf('_') == -1) {
                PlayGame(GameType.WON_GAME);
            }
        } else {
            // Word does NOT contain letter
            wrongLetterCount += 1;
            ImageView pic = findViewById(R.id.hangmanPic);
            pic.setBackground(ContextCompat.getDrawable(this, GetDrawable()));
            if (wrongLetterCount >= 6) {
                PlayGame(GameType.LOST_GAME);
            }
        }
    }

    public Integer GetDrawable() {
        int picId;
        switch (wrongLetterCount) {
            case 0: {
                picId = R.drawable.svg_hangman0;
                break;
            }
            case 1: {
                picId = R.drawable.svg_hangman1;
                break;
            }
            case 2: {
                picId = R.drawable.svg_hangman2;
                break;
            }
            case 3: {
                picId = R.drawable.svg_hangman3;
                break;
            }
            case 4: {
                picId = R.drawable.svg_hangman4;
                break;
            }
            case 5: {
                picId = R.drawable.svg_hangman5;
                break;
            }
            default: {
                picId = R.drawable.svg_hangman6;
                break;
            }
        }
        return picId;
    }

    public void setButtonState(View v, Boolean isReset) {
        if (isReset) {
            v.setEnabled(true);
            ((Button) v).setTextColor(Color.parseColor("#6ec5f0"));
        } else {
            v.setEnabled(false);
            ((Button) v).setTextColor(Color.parseColor("#808080"));
        }
    }

    public void ResetButtonState() {
        setButtonState(findViewById(R.id.buttonA), true);
        setButtonState(findViewById(R.id.buttonB), true);
        setButtonState(findViewById(R.id.buttonC), true);
        setButtonState(findViewById(R.id.buttonD), true);
        setButtonState(findViewById(R.id.buttonE), true);
        setButtonState(findViewById(R.id.buttonF), true);
        setButtonState(findViewById(R.id.buttonG), true);
        setButtonState(findViewById(R.id.buttonH), true);
        setButtonState(findViewById(R.id.buttonI), true);
        setButtonState(findViewById(R.id.buttonJ), true);
        setButtonState(findViewById(R.id.buttonK), true);
        setButtonState(findViewById(R.id.buttonL), true);
        setButtonState(findViewById(R.id.buttonM), true);
        setButtonState(findViewById(R.id.buttonN), true);
        setButtonState(findViewById(R.id.buttonO), true);
        setButtonState(findViewById(R.id.buttonP), true);
        setButtonState(findViewById(R.id.buttonQ), true);
        setButtonState(findViewById(R.id.buttonR), true);
        setButtonState(findViewById(R.id.buttonS), true);
        setButtonState(findViewById(R.id.buttonT), true);
        setButtonState(findViewById(R.id.buttonU), true);
        setButtonState(findViewById(R.id.buttonV), true);
        setButtonState(findViewById(R.id.buttonW), true);
        setButtonState(findViewById(R.id.buttonX), true);
        setButtonState(findViewById(R.id.buttonY), true);
        setButtonState(findViewById(R.id.buttonZ), true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonA: {
                setButtonState(v, false);
                CheckLetterInWord('A');
                break;
            }
            case R.id.buttonB: {
                setButtonState(v, false);
                CheckLetterInWord('B');
                break;
            }
            case R.id.buttonC: {
                setButtonState(v, false);
                CheckLetterInWord('C');
                break;
            }
            case R.id.buttonD: {
                setButtonState(v, false);
                CheckLetterInWord('D');
                break;
            }
            case R.id.buttonE: {
                setButtonState(v, false);
                CheckLetterInWord('E');
                break;
            }
            case R.id.buttonF: {
                setButtonState(v, false);
                CheckLetterInWord('F');
                break;
            }
            case R.id.buttonG: {
                setButtonState(v, false);
                CheckLetterInWord('G');
                break;
            }
            case R.id.buttonH: {
                setButtonState(v, false);
                CheckLetterInWord('H');
                break;
            }
            case R.id.buttonI: {
                setButtonState(v, false);
                CheckLetterInWord('I');
                break;
            }
            case R.id.buttonJ: {
                setButtonState(v, false);
                CheckLetterInWord('J');
                break;
            }
            case R.id.buttonK: {
                setButtonState(v, false);
                CheckLetterInWord('K');
                break;
            }
            case R.id.buttonL: {
                setButtonState(v, false);
                CheckLetterInWord('L');
                break;
            }
            case R.id.buttonM: {
                setButtonState(v, false);
                CheckLetterInWord('M');
                break;
            }
            case R.id.buttonN: {
                setButtonState(v, false);
                CheckLetterInWord('N');
                break;
            }
            case R.id.buttonO: {
                setButtonState(v, false);
                CheckLetterInWord('O');
                break;
            }
            case R.id.buttonP: {
                setButtonState(v, false);
                CheckLetterInWord('P');
                break;
            }
            case R.id.buttonQ: {
                setButtonState(v, false);
                CheckLetterInWord('Q');
                break;
            }
            case R.id.buttonR: {
                setButtonState(v, false);
                CheckLetterInWord('R');
                break;
            }
            case R.id.buttonS: {
                setButtonState(v, false);
                CheckLetterInWord('S');
                break;
            }
            case R.id.buttonT: {
                setButtonState(v, false);
                CheckLetterInWord('T');
                break;
            }
            case R.id.buttonU: {
                setButtonState(v, false);
                CheckLetterInWord('U');
                break;
            }
            case R.id.buttonV: {
                setButtonState(v, false);
                CheckLetterInWord('V');
                break;
            }
            case R.id.buttonW: {
                setButtonState(v, false);
                CheckLetterInWord('W');
                break;
            }
            case R.id.buttonX: {
                setButtonState(v, false);
                CheckLetterInWord('X');
                break;
            }
            case R.id.buttonY: {
                setButtonState(v, false);
                CheckLetterInWord('Y');
                break;
            }
            case R.id.buttonZ: {
                setButtonState(v, false);
                CheckLetterInWord('Z');
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private boolean isTestDevice() {
        return Boolean.valueOf(Settings.System.getString(getContentResolver(), "firebase.test.lab"));
    }
}