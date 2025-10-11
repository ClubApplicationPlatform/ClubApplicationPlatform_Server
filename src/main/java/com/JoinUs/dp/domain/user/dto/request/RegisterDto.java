package com.JoinUs.dp.domain.user.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

// 테스트 데이터 생성용 코드(domain.interview.SetData 파일 삭제시 삭제해도됨)
@NoArgsConstructor
@AllArgsConstructor

public class RegisterDto {
    private String email;
    private String password;
    private String username;
}
