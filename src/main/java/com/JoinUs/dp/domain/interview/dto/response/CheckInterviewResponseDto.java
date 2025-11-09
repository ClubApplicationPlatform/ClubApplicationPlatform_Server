package com.JoinUs.dp.domain.interview.dto.response;

import java.util.List;

// 동아리 내 면접 회차 조회 dto
// 면접 회차 조회에 사용됨
public record CheckInterviewResponseDto(
        Long clubId,
        String clubName,
        List<InterviewResponseDto> interviews
) {
}
