package com.JoinUs.dp.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class UserRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final long REFRESH_TOKEN_TTL_DAYS = 7;

    // 리프레시 토큰 저장
    public void saveRefreshToken(String email, String refreshToken) {
        String key = "REFRESH_TOKEN:" + email;
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_TTL_DAYS, TimeUnit.DAYS);
    }

    // 리프레시 토큰 조회
    public String getRefreshToken(String email) {
        String key = "REFRESH_TOKEN:" + email;
        return redisTemplate.opsForValue().get(key);
    }

    // 리프레시 토큰 삭제 (로그아웃 등)
    public void deleteRefreshToken(String email) {
        String key = "REFRESH_TOKEN:" + email;
        redisTemplate.delete(key);
    }

    // 검증
    public boolean verifyRefreshToken(String email, String refreshToken) {
        String savedToken = getRefreshToken(email);
        return savedToken != null && savedToken.equals(refreshToken);
    }
}
