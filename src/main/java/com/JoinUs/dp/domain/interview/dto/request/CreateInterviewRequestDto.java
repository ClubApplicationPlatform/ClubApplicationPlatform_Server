package com.JoinUs.dp.domain.interview.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

// 면접 회차 생성 바디 dto
public record CreateInterviewRequestDto (
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime startAt,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime endAt,

        @NotNull
        String location,

        @NotNull
        Long capacity
){
}
