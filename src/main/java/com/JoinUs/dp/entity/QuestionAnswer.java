// src/main/java/com/JoinUs/dp/global/domain/application/entity/QuestionAnswer.java
package com.JoinUs.dp.entity;

public class QuestionAnswer {
    private String question;
    private String answer;

    public QuestionAnswer() {}
    public QuestionAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public void setQuestion(String question) { this.question = question; }
    public void setAnswer(String answer) { this.answer = answer; }
}
