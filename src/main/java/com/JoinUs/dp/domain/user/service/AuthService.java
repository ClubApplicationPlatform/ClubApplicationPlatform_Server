package com.JoinUs.dp.domain.user.service;

import com.JoinUs.dp.domain.user.dto.request.RegisterDto;
import com.JoinUs.dp.domain.user.dto.response.TokenDto;
import com.JoinUs.dp.domain.user.repository.UserRedisRepository;
import com.JoinUs.dp.global.utility.JwtProvider;
import com.JoinUs.dp.domain.user.entity.User;
import com.JoinUs.dp.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public TokenDto login(String email, String password) {
        User user = userRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshToken = jwtProvider.generateRefreshToken(email);

        userRedisRepository.saveRefreshToken(email, refreshToken);

        return new TokenDto(accessToken, refreshToken);
    }

    public void register(RegisterDto dto) {
        if (userRepository.existsById(dto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .build();
        userRepository.save(user);
    }

    public TokenDto refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }

        String email = jwtProvider.extractEmail(refreshToken);

        if (!userRedisRepository.verifyRefreshToken(email, refreshToken)) {
            throw new RuntimeException("리프레시 토큰이 유효하지 않습니다.");
        }

        String newAccessToken = jwtProvider.generateAccessToken(email);
        String newRefreshToken = jwtProvider.generateRefreshToken(email);

        userRedisRepository.saveRefreshToken(email, newRefreshToken);

        return new TokenDto(newAccessToken, newRefreshToken);
    }

    public void logout(String email) {
        userRedisRepository.deleteRefreshToken(email);
    }
}
