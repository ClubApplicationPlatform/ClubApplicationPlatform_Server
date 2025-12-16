package com.JoinUs.dp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionUpdateRequest {
    private String question;
    private Integer maxLength;
}
