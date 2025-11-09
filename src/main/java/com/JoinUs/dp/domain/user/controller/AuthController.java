package com.JoinUs.dp.domain.user.controller;

import com.JoinUs.dp.domain.user.dto.request.LoginDto;
import com.JoinUs.dp.domain.user.dto.request.RefreshTokenDto;
import com.JoinUs.dp.domain.user.dto.request.RegisterDto;
import com.JoinUs.dp.domain.user.service.AuthService;
import com.JoinUs.dp.global.common.ApiPath;
import com.JoinUs.dp.global.dto.Response;
import com.JoinUs.dp.global.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<Response<String>> register(@RequestBody RegisterDto dto) {
        authService.register(dto);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, null, "회원가입 성공"));
    }

    // 로그인 요청
    @PostMapping("/login")
    public ResponseEntity<Response<TokenResponse>> login(@RequestBody LoginDto dto) {
        var tokens = authService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK,
                new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken()),
                "토큰이 전달되었습니다."));
    }

    // 리프레시 토큰으로 Access 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<Response<TokenResponse>> refreshToken(@RequestBody RefreshTokenDto request) {
        var tokens = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK,
                new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken()),
                "리프레시 토큰이 재발급 되었습니다."));
    }
}
