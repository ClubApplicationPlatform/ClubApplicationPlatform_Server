package com.JoinUs.dp.domain.jwt.dto;

public record JwtDto(
        String accessToken,
        String refreshToken
) {
}
