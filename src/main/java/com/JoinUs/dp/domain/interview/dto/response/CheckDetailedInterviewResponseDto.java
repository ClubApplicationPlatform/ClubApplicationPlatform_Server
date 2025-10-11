package com.JoinUs.dp.domain.interview.dto.response;

import java.util.List;

// 동아리 내 면접 회차 상세 조회 dto
// 면접 현황 조회에 사용됨
public record CheckDetailedInterviewResponseDto(
        Long clubId,
        String clubName,
        List<DetailedInterviewResponseDto> interviews
) {
}
