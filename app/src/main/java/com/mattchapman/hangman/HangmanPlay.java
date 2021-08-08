package com.mattchapman.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mattchapman.hangman.databinding.ActivityHangmanPlayBinding;
import com.mattchapman.hangman.enums.Category;
import com.mattchapman.hangman.enums.GameType;
import com.mattchapman.hangman.model.HangmanCountModel;
import com.mattchapman.hangman.model.HangmanWordModel;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mattchapman.hangman.model.UserDataModel;

import java.text.NumberFormat;


public class HangmanPlay extends AppCompatActivity {
    TextView txtWord;
    String currentWord;
    String reportedWord = "";
    Integer wrongLetterCount;
    private AdView mAdView;
    UserDataModel userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_play);

        ActivityHangmanPlayBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_hangman_play);
        binding.setHandlers(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if (isTestDevice()) {
            mAdView.setVisibility(View.GONE);
        }

        txtWord = findViewById(R.id.text_word);
        PlayGame(GameType.NEW_GAME);
    }

    // This is the actual letter click function
    public void clickLetter(View v, Character letter) {
        setButtonState(v, false);
        CheckLetterInWord(letter);
    }

    public void clickHint(View v) {
        HangmanDBHelper db = new HangmanDBHelper(HangmanPlay.this);
        if(userData == null){
            userData = new UserDataModel(0,0,0);
        }
        int hintsTaken = userData.getHintsTaken() + 1;
        userData.setHintsTaken(hintsTaken);
        db.setUserHintTaken(hintsTaken);

        int hintsPoints = userData.getHintPoints() - 5;
        userData.setHintPoints(hintsPoints);
        db.setUserHintPoints(hintsPoints);
        hintButtonEnabled(HasEnoughHitPoints());
        TextView txtUnplayedCount = findViewById(R.id.CountHint);
        txtUnplayedCount.setText(NumberFormat.getIntegerInstance().format(hintsPoints));

        Character letter = hintLetter();
        String dynamicButtonFind = "button" + letter;

        View buttonView = findViewById(getResources().getIdentifier(dynamicButtonFind, "id", getPackageName()));
        setButtonState(buttonView, false);

        CheckLetterInWord(letter);
    }

    public void clickReport(View v) {
        try{
            throw new Exception("Reported Word: " + reportedWord);
        }catch (Exception ex){
            FirebaseCrashlytics fbc = FirebaseCrashlytics.getInstance();
            fbc.setCustomKey("Class.Method", "HangmanPlay.clickReport");
            fbc.recordException(ex);

            Toast.makeText(getApplicationContext(), "Word reported!", Toast.LENGTH_SHORT).show();
            reportButtonEnabled(false);
        }
    }

    public void reportButtonEnabled(Boolean isEnabled){
        View v = findViewById(R.id.buttonReportWord);
        v.setEnabled(isEnabled);
        int backgroundId = R.drawable.grey_button;

        if (isEnabled){
            backgroundId = R.drawable.red_button;
        }

        ((Button) v).setBackground(ContextCompat.getDrawable(this, backgroundId));
    }

    public Character hintLetter(){
        String underscoreWord = txtWord.getText().toString();
        Integer firstUnknownLetter = underscoreWord.indexOf("_");
        Character letter = currentWord.charAt(firstUnknownLetter);
        return letter;
    }

    public void hintButtonEnabled(Boolean isEnabled){
        View v = findViewById(R.id.buttonHint);
        v.setEnabled(isEnabled);
        int backgroundId = R.drawable.grey_button;

        if (isEnabled){
            backgroundId = R.drawable.blue_button;
        }

        ((Button) v).setBackground(ContextCompat.getDrawable(this, backgroundId));
    }

    public void PlayGame(GameType gameType) {
        // Prevent user from going further until we decide what point they are
        ConstraintLayout card = findViewById(R.id.playAgainView);
        card.setVisibility(View.VISIBLE);

        // ensure the word reported is the word that was played
        reportedWord = currentWord;

        // Only init the DB helper one time
        HangmanDBHelper db = new HangmanDBHelper(HangmanPlay.this);
        HangmanCountModel countData;
        userData = db.getUserData();

        String gameOverTxt = "";
        if (gameType == GameType.NEW_GAME) {
            countData = db.getWordCounts();
        } else if (gameType == GameType.WON_GAME) {
            db.setWordWon(currentWord);
            gameOverTxt = "You WON!";

            countData = db.getWordCounts();
            int wonTimes = countData.getWon();
            if (wonTimes > 0){
                db.setUserLevel((int)Math.floor(wonTimes / 25));
            }
            int hintPoints = userData.getHintPoints() + 1;
            db.setUserHintPoints(hintPoints);
            userData.setHintPoints(hintPoints);
        } else if (gameType == GameType.LOST_GAME) {
            db.setWordLoss(currentWord);
            gameOverTxt = "You LOST." + System.getProperty("line.separator");
            gameOverTxt += "The word was" + System.getProperty("line.separator");
            gameOverTxt += currentWord;

            countData = db.getWordCounts();

            //TODO: this may be too mean
            //db.setUserHintPoints(userData.getHintPoints() - 1);
        } else {
            //Should never hit here
            gameOverTxt = "Unknown play type, please close the app and try again.";
            countData = new HangmanCountModel(0, 0, 0);
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
        SetHintStats();

        // Display Game Over text or Just start a new game
        if (countData.getUnplayed() != 0) {
            // Can play unplayed so should get that next value
            HangmanWordModel wordData = db.getUnplayedWord();
            currentWord = wordData.getWord();
            SetWordCategory(wordData.getCategory());

            if (gameType == GameType.NEW_GAME) {
                card.setVisibility(View.GONE);
            } else {
                playOver.setVisibility(View.GONE);
                playLossGames.setVisibility(View.GONE);

                playAgainTxt.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);
                playAgain.setText(getString(R.string.play_Again));
            }
        } else if (countData.getLoss() != 0) {
            // Can replay lost games so let the user do that
            playOver.setVisibility(View.VISIBLE);
            playLossGames.setVisibility(View.VISIBLE);

            playAgainTxt.setVisibility(View.VISIBLE);
            playAgain.setVisibility(View.VISIBLE);
            playAgain.setText(getString(R.string.play_Lost_Words));

            HangmanWordModel wordData = db.getLostWord();
            currentWord = wordData.getWord();
            SetWordCategory(wordData.getCategory());
        } else {
            // The game is over since unplayed and lost games have been exhausted
            playOver.setVisibility(View.VISIBLE);
            playLossGames.setVisibility(View.GONE);

            playAgainTxt.setVisibility(View.GONE);
            playAgain.setVisibility(View.GONE);
            reportButtonEnabled(false);
        }

        SetUnderscores();
    }

    public void PlayAgainClick(View view) {
        // Data should already be loaded so all that has to happen is the screen will disappear
        ConstraintLayout card = findViewById(R.id.playAgainView);
        card.setVisibility(View.GONE);
    }

    public void ResetVars() {
        wrongLetterCount = 0;
        ImageView pic = findViewById(R.id.hangmanPic);
        pic.setBackground(ContextCompat.getDrawable(this, R.drawable.svg_hangman0));
        hintButtonEnabled(HasEnoughHitPoints());
        reportButtonEnabled(true);
    }

    public boolean HasEnoughHitPoints(){
        return userData != null && userData.getHintPoints() >= 5;
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
            txtWord.setText(getString(R.string.you_Win));
        }
    }

    public void SetWordCategory(int categoryInt) {
        final TextView txtCategory = findViewById(R.id.text_category);
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

    public void SetHintStats() {
        final TextView txtUnplayedCount = findViewById(R.id.CountHint);
        txtUnplayedCount.setText(NumberFormat.getIntegerInstance().format(userData.getHintPoints()));

        final TextView txtWonCount = findViewById(R.id.CountLevel);
        txtWonCount.setText(NumberFormat.getIntegerInstance().format(userData.getLevel()));
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

    private boolean isTestDevice() {
        return Boolean.parseBoolean(Settings.System.getString(getContentResolver(), "firebase.test.lab"));
    }
}