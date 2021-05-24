package com.example.hangman.model;

public class HangmanCountModel {
    private int Won;
    private int Loss;
    private int Unplayed;

    // constructors
    public HangmanCountModel(int won, int loss, int unplayed) {
        Won = won;
        Loss = loss;
        Unplayed = unplayed;
    }

    public HangmanCountModel() {
    }

    // toString is needed when we print
    @Override
    public String toString() {
        return "HangmanCountModel{" +
                "Won = " + Won + ", " +
                "Loss = " + Loss + ", " +
                "unplayed = " + Unplayed + ", " +
                '}';
    }

    // getters and setters
    public int getWon() {
        return Won;
    }
    public void setWon(int won) {
        Won = won;
    }

    public int getLoss() { return Loss; }
    public void setLoss(int loss) { Loss = loss; }

    public int getUnplayed() { return Unplayed; }
    public void setUnplayed(int unplayed) {
        Unplayed = unplayed;
    }
}
