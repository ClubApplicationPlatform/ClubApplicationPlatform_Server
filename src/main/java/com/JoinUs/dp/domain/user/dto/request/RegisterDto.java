package com.JoinUs.dp.domain.user.dto.request;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String username;
}
