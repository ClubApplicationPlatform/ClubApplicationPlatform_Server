package com.JoinUs.dp.global.common;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiPath {
    public static final String API_ROOT = "/api/v1";
    public static final String AUTH_PATH = API_ROOT + "/auth";


    // 도메인별 API_PATH 예시
    // public static final String USER_PATH = API_ROOT + "/user";

    public static String H2_PATH;

    @Value("${spring.h2.console.path}")
    private String h2Path;

    @PostConstruct
    public void init() {
        H2_PATH = h2Path;
    }

    // Clubs (면접 API)
    public static final String CLUBS = API_ROOT + "/clubs";
    // 면접 회차 생성/조회
    public static final String CLUB_INTERVIEWS = CLUBS + "/{clubId}/interviews";
    // 신청자 면접 현황 조회
    public static final String CLUB_INTERVIEW_APPS = CLUBS + "/{clubId}/interview-applications";
    // 미확정/가입거절 인원 조회
    public static final String CLUB_VACANCIES = CLUBS + "/{clubId}/vacancies";

    // Interviews (면접 API)
    public static final String INTERVIEWS = API_ROOT + "/interviews";
    // 면접 신청
    public static final String INTERVIEW_APPLY = INTERVIEWS + "/{interviewId}/apply";
}
