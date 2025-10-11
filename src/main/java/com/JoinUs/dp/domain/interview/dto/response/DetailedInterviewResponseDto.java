package com.JoinUs.dp.domain.interview.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

// 특정 면접 회차 상세 정보 (면접 신청자 정보 포합) dto
public record DetailedInterviewResponseDto(
        Long interviewId,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime startAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime endAt,
        String location,
        Long capacity,
        Long applied,

        List<ApplicantResponseDto> applicants
) {
}
