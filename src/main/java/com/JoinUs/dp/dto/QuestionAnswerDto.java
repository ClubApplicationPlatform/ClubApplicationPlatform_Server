// src/main/java/com/JoinUs/dp/global/domain/application/dto/QuestionAnswerDto.java
package com.JoinUs.dp.dto;

import com.JoinUs.dp.entity.QuestionAnswer;

public class QuestionAnswerDto {
    private String question;
    private String answer;

    public QuestionAnswerDto() {}

    public QuestionAnswerDto(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public void setQuestion(String question) { this.question = question; }
    public void setAnswer(String answer) { this.answer = answer; }

    // Entity → DTO 변환
    public static QuestionAnswerDto from(QuestionAnswer qa) {
        return new QuestionAnswerDto(qa.getQuestion(), qa.getAnswer());
    }

    // DTO → Entity 변환
    public QuestionAnswer toEntity() {
        return new QuestionAnswer(this.question, this.answer);
    }
}
