package com.JoinUs.dp.domain.interview.dto.response;

import com.JoinUs.dp.domain.interview.entity.Interview;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

// 단일 면접 회차 정보 dto
public record InterviewResponseDto(
        Long interviewId,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime startAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime endAt,
        String location,
        Long capacity,
        Long applied
) {
    public static InterviewResponseDto from(Interview interview) {
        return new InterviewResponseDto(
                interview.getId(),
                interview.getStartAt(),
                interview.getEndAt(),
                interview.getLocation(),
                interview.getCapacity(),
                interview.getApplied()
        );
    }
}
