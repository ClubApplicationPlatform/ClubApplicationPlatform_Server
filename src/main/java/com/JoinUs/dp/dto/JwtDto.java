package com.JoinUs.dp.dto;

public record JwtDto(
        String accessToken,
        String refreshToken
) {
}
