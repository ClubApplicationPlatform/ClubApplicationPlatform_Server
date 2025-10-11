package com.JoinUs.dp.domain.interview.dto.response;

import java.util.List;

// 면접 합격자 중 가입 미확정/가입거절 유저 정보 dto
public record UnconfirmedUserResponseDto(
        Long clubId,
        String clubName,
        Long applicantCount,
        Long pendingUserCount,
        List<ApplicantResponseDto> pendingUsers
) {
}
