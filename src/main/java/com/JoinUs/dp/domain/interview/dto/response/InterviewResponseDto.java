package com.JoinUs.dp.domain.interview.dto.response;

import com.JoinUs.dp.domain.interview.entity.Interview;

import java.time.LocalDate;
import java.time.LocalTime;

// 단일 면접 회차 정보 dto
public record InterviewResponseDto(
        Long interviewId,
        String location,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        int duration,
        int maxApplicants,
        int applied
) {
    public static InterviewResponseDto from(Interview interview) {
        return new InterviewResponseDto(
                interview.getId(),
                interview.getLocation(),
                interview.getDate(),
                interview.getStartTime(),
                interview.getEndTime(),
                interview.getDuration(),
                interview.getMaxApplicants(),
                interview.getApplied()
        );
    }
}
