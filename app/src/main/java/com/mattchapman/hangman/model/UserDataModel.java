package com.mattchapman.hangman.model;

public class UserDataModel {
    private int  HintsTaken;
    private int  HintPoints;
    private int Level;

    // constructors
    public UserDataModel(int hintsTaken, int hintPoints, int level) {
        HintsTaken = hintsTaken;
        HintPoints = hintPoints;
        Level = level;
    }

    public UserDataModel() {
    }

    public int getHintsTaken() {
        return HintsTaken;
    }
    public void setHintsTaken(int hintsTaken) {
        HintsTaken = hintsTaken;
    }

    public int getHintPoints() {
        return HintPoints;
    }
    public void setHintPoints(int hintPoints) {
        HintPoints = hintPoints;
    }

    public int getLevel() {
        return Level;
    }
    public void setLevel(int level) {
        Level = level;
    }
}
