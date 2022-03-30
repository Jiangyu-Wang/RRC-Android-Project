package com.example.rrcproject;

public class Question {

    public int index;
    public String questionText;
    public String questionExplanation;
    public String option1;
    public String option2;
    public int correctAnswerIndex;

    public Question(int index, String questionText, String questionExplanation, String option1, String option2, int correctAnswerIndex) {
        this.index = index;
        this.questionText = questionText;
        this.questionExplanation = questionExplanation;
        this.option1 = option1;
        this.option2 = option2;
        this.correctAnswerIndex = correctAnswerIndex;
    }

}
