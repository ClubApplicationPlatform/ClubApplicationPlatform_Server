package com.JoinUs.dp.domain.user.dto.request;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
