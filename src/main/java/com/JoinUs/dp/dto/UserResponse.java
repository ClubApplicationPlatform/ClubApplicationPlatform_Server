package com.JoinUs.dp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String email;
    private String username;
    private String nickname;
    private String department;
    private String studentId;
    private String phone;
    private String role;
    private Integer grade;
}
