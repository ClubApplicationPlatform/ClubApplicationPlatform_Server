package com.JoinUs.dp.domain.user.controller;

import com.JoinUs.dp.domain.user.dto.request.LoginDto;
import com.JoinUs.dp.domain.user.dto.request.RegisterDto;
import com.JoinUs.dp.domain.user.service.AuthService;
import com.JoinUs.dp.global.common.ApiPath;
import com.JoinUs.dp.global.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody RegisterDto dto) {
        authService.signUp(dto);
        return ResponseEntity.ok("회원가입 성공");
    }


    // 로그인 요청 (email, password 받아서 토큰 반환)
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginDto dto) {
        var tokens = authService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    }



    // 리프레시 토큰으로 Access 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenResponse refreshRequest) {
        var tokens = authService.refreshAccessToken(refreshRequest.getRefresh_token());
        return ResponseEntity.ok(new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    }
}
