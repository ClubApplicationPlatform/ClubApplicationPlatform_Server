package com.JoinUs.dp.domain.mail.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class MailRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final long CODE_TTL_MINUTES = 10;

    // 인증 코드 저장
    public void saveCode(String email, String code) {
        String key = "EMAIL_CODE:" + email;
        redisTemplate.opsForValue().set(key, code, CODE_TTL_MINUTES, TimeUnit.MINUTES);
    }

    // 인증 코드 조회
    public String getCode(String email) {
        String key = "EMAIL_CODE:" + email;
        return redisTemplate.opsForValue().get(key);
    }

    // 인증 코드 삭제 (optional)
    public void deleteCode(String email) {
        String key = "EMAIL_CODE:" + email;
        redisTemplate.delete(key);
    }

    // 코드 검증
    public boolean verifyCode(String email, String inputCode) {
        String code = getCode(email);
        return code != null && code.equals(inputCode);
    }
}
