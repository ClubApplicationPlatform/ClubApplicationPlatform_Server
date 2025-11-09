package com.JoinUs.dp.domain.interview.controller;


import com.JoinUs.dp.domain.interview.dto.request.CreateInterviewRequestDto;
import com.JoinUs.dp.domain.interview.dto.response.CheckDetailedInterviewResponseDto;
import com.JoinUs.dp.domain.interview.dto.response.CheckInterviewResponseDto;
import com.JoinUs.dp.domain.interview.dto.response.InterviewResponseDto;
import com.JoinUs.dp.domain.interview.dto.response.UnconfirmedUserResponseDto;
import com.JoinUs.dp.domain.interview.service.InterviewService;
import com.JoinUs.dp.global.common.ApiPath;
import com.JoinUs.dp.global.dto.Response;
import com.JoinUs.dp.global.utility.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;
    private final JwtProvider jwtProvider;

    // 1) 면접 회차 생성
    @Operation(summary = "면접 회차 생성")
    @PostMapping(ApiPath.CLUB_INTERVIEWS)
    public ResponseEntity<Response<InterviewResponseDto>> create(
            @PathVariable Long clubId,
            @RequestBody CreateInterviewRequestDto req
    ) {
        return ResponseEntity.ok(
                new Response<>(
                        HttpStatus.OK,
                        interviewService.create(clubId, req),
                        "면접 회차 생성 완료"
                )
        );
    }

    // 2) 면접 회차 조회
    @Operation(summary = "면접 회차 조회")
    @GetMapping(ApiPath.CLUB_INTERVIEWS)
    public ResponseEntity<Response<CheckInterviewResponseDto>> check(@PathVariable Long clubId) {
        return ResponseEntity.ok(
                new Response<>(
                        HttpStatus.OK,
                        interviewService.check(clubId),
                        "면접 회차 조회"
                )
        );
    }

//    // 3) 면접 신청
//    @PostMapping(ApiPath.INTERVIEW_APPLY)
//    public void apply(
//            @PathVariable Long interviewId,
//            @RequestBody ApplyInterviewRequestDto req
//    ) {
//        interviewService.apply(interviewId, req);
//    }

    private String resolveBearer(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰이 없습니다");
        }
        return authorization.substring(7); // "Bearer " 제거
    }

    // 3) 면접 신청
    @Operation(summary = "면접 신청")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/interviews/{interviewId}/apply")
    public void apply(@PathVariable Long interviewId, HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION); // Swagger가 전역으로 붙여줌
        if (authorization == null || authorization.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization 헤더가 없습니다");
        }
        String token = resolveBearer(authorization);

        // 유효성 확인
        if (!jwtProvider.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰");
        }
        // 토큰 -> 이메일
        String email = jwtProvider.extractEmail(token);

//        interviewService.applyWithToken(interviewId, token);
        interviewService.apply(interviewId, email);
    }


    // 4) 신청자 면접 현황 조회
    @Operation(summary = "신청자 면접 현황 조회")
    @GetMapping(ApiPath.CLUB_INTERVIEW_APPS)
    public ResponseEntity<Response<CheckDetailedInterviewResponseDto>> checkDetailed(@PathVariable Long clubId) {
        return ResponseEntity.ok(
                new Response<>(
                        HttpStatus.OK,
                        interviewService.checkDetailed(clubId),
                        "신청자 면접 현황 조회"
                )
        );
    }

    // 5) 미확정/가입거절 인원 조회
    @Operation(summary = "미확정/가입거절 인원 조회")
    @GetMapping(ApiPath.CLUB_VACANCIES)
    public ResponseEntity<Response<UnconfirmedUserResponseDto>> checkUnconfirmedUser(@PathVariable Long clubId) {
        return ResponseEntity.ok(
                new Response<>(
                        HttpStatus.OK,
                        interviewService.checkUnconfirmedUser(clubId),
                        "미확정/가입거절 인원 조회"
                )
        );
    }
}
