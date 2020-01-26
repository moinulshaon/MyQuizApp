package com.example.myquizapp;

public class QuestionModel {
    private final int question;
    private final boolean answer;

    public QuestionModel(int question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public int getQuestion() {
        return question;
    }

    public boolean isAnswer() {
        return answer;
    }
}
