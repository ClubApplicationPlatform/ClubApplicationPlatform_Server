package com.JoinUs.dp.controller;

import com.JoinUs.dp.common.response.Response;
import com.JoinUs.dp.dto.ApplicationResponse;
import com.JoinUs.dp.dto.UserResponse;
import com.JoinUs.dp.dto.UserUpdateRequest;
import com.JoinUs.dp.global.common.api.ApiPath;
import com.JoinUs.dp.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(ApiPath.USER_ME)
    public ResponseEntity<Response<UserResponse>> getMyInfo(@RequestParam Long userId) {

        UserResponse data = userService.getMyInfo(userId);

        return ResponseEntity.ok(
                new Response<>(HttpStatus.OK.value(), data, "내 정보 조회 성공")
        );
    }

    @PatchMapping(ApiPath.USER_ME)
    public ResponseEntity<Response<UserResponse>> updateMyInfo(
            @RequestParam Long userId,
            @RequestBody UserUpdateRequest req
    ) {
        UserResponse data = userService.updateMyInfo(userId, req);

        return ResponseEntity.ok(
                new Response<>(HttpStatus.OK.value(), data, "내 정보 수정 성공")
        );
    }

    @GetMapping(ApiPath.USER_APPLICATIONS)
    public ResponseEntity<Response<List<ApplicationResponse>>> getMyApplications(
            @RequestParam Long userId
    ) {
        List<ApplicationResponse> list = userService.getMyApplications(userId);

        return ResponseEntity.ok(
                new Response<>(HttpStatus.OK.value(), list, "내 지원 내역 조회 성공")
        );
    }
}
