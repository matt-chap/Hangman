package com.example.hangman.model;

public class HangmanWordModel {
    private String Word;
    private int  Category;
    private int Won;

    // constructors
    public HangmanWordModel(String word, int category, int won) {
        Word = word;
        Category = category;
        Won = won;
    }

    public HangmanWordModel() {
    }

    // toString is needed when we print
    @Override
    public String toString() {
        return "HangmanWordsModel{" +
                "Word='" + Word + '\'' +
                ", Category=" + Category +
                ", Won=" + Won +
                '}';
    }

    // getters and setters
    public String getWord() {
        return Word;
    }
    public void setWord(String word) {
        Word = word;
    }

    public int getCategory() {
        return Category;
    }
    public void setCategory(int category) {
        Category = category;
    }

    public int getWon() {
        return Won;
    }
    public void setWon(int won) {
        Won = won;
    }
}
