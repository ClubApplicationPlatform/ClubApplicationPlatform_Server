package com.JoinUs.dp.global.common;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiPath {
    public static final String API_ROOT = "/api/v1";


    // 도메인별 API_PATH 예시
    // public static final String USER_PATH = API_ROOT + "/user";

    public static String H2_PATH;

    @Value("${spring.h2.console.path}")
    private String h2Path;

    @PostConstruct
    public void init() {
        H2_PATH = h2Path;
    }
}
