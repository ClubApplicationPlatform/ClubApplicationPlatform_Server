package com.JoinUs.dp.common.security;


import org.springframework.stereotype.Component;

// 실제 JWT 파싱 대신 "Bearer 이메일" 형식에서 이메일만 잘라내는 스텁 예시
@Component
public class JwtProvider {
    public String extractEmail(String tokenHeader) {
        if (tokenHeader == null) return null;
        // 예: "Bearer user@example.com"
        String prefix = "Bearer ";
        if (tokenHeader.startsWith(prefix)) {
            return tokenHeader.substring(prefix.length()).trim();
        }
        return null;
    }
}