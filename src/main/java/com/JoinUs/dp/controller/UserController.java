package com.JoinUs.dp.controller;

import com.JoinUs.dp.dto.UserUpdateRequest;
import com.JoinUs.dp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // JWT에서 userId 추출했다고 가정하고 파라미터로 받음
    private Long getUserId() {
        return 1L; // TODO: JWT 연동시 변경
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo() {
        return ResponseEntity.ok(userService.getMyInfo(getUserId()));
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateMyInfo(@RequestBody UserUpdateRequest req) {
        return ResponseEntity.ok(userService.updateMyInfo(getUserId(), req));
    }

    @GetMapping("/applications/mine")
    public ResponseEntity<?> getMyApplications() {
        return ResponseEntity.ok(userService.getMyApplications(getUserId()));
    }
}
