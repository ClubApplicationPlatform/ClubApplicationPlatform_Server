package com.JoinUs.dp.domain.interview.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// 특정 면접 회차 상세 정보 (면접 신청자 정보 포합) dto
public record DetailedInterviewResponseDto(
        Long interviewId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String location,
        int maxApplicants,
        int applied,

        List<ApplicantResponseDto> applicants
) {
}
