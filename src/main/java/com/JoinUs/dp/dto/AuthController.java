package com.JoinUs.dp.dto;

import com.JoinUs.dp.service.AuthService;
import com.JoinUs.dp.global.common.api.ApiPath;
import com.JoinUs.dp.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<String>> signUp(@RequestBody RegisterDto dto) {
        authService.signUp(dto);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, null, "회원가입 성공"));
    }


    // 로그인 요청 (email, password 받아서 토큰 반환)
    @PostMapping("/login")
    public ResponseEntity<Response<TokenResponse>> login(@RequestBody LoginDto dto) {
        var tokens = authService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken()), "토큰이 전달 되었습니다."));
    }



    // 리프레시 토큰으로 Access 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<Response<TokenResponse>> refreshToken(@RequestBody TokenResponse refreshRequest) {
        var tokens = authService.refreshAccessToken(refreshRequest.getRefresh_token());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, new TokenResponse(tokens.getAccessToken(), tokens.getRefreshToken()), "리프레쉬 토큰이 재발급 되었습니다."));
    }
}
