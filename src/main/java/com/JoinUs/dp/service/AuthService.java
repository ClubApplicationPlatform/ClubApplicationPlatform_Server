package com.JoinUs.dp.service;

import com.JoinUs.dp.dto.RegisterDto;
import com.JoinUs.dp.dto.TokenDto;
import com.JoinUs.dp.global.utility.JwtProvider;
import com.JoinUs.dp.entity.User;
import com.JoinUs.dp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signUp(RegisterDto dto) {
        // ✅ existsByEmail 로 변경
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setNickname(dto.getUsername()); // 혹은 별도로 지정
        user.setDepartment("미정");
        user.setStudentId("0");
        user.setGrade(1);

        userRepository.save(user);
    }

    public TokenDto login(String email, String password) {
        // ✅ findByEmail 로 변경
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshToken = jwtProvider.generateRefreshToken(email);

        return new TokenDto(accessToken, refreshToken);
    }

    public TokenDto refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }

        String email = jwtProvider.extractEmail(refreshToken);

        String newAccessToken = jwtProvider.generateAccessToken(email);
        String newRefreshToken = jwtProvider.generateRefreshToken(email);

        return new TokenDto(newAccessToken, newRefreshToken);
    }
}
