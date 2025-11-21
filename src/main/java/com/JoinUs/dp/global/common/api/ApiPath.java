package com.JoinUs.dp.global.common.api;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 전역 API 경로 및 설정 상수 관리 클래스
 */
@Component
public class ApiPath {

    // ==========================
    // 🔹 공통 API 경로
    // ==========================
    public static final String API_ROOT = "/api";
    public static final String API_V1 = API_ROOT + "/v1";

    // ==========================
    // 🔹 인증 / 사용자
    // ==========================
    public static final String AUTH_PATH = API_V1 + "/auth";
    public static final String USER_PATH = API_V1 + "/users";
    public static final String USER_ME = USER_PATH + "/me";
    public static final String USER_APPLICATIONS = API_V1 + "/applications/mine";

    // ==========================
    // 🔹 공지사항 / FAQ
    // ==========================
    public static final String NOTICE_PATH = API_V1 + "/notices";
    public static final String CLUB_NOTICE_PATH = API_V1 + "/clubs/{clubId}/notice";

    // ==========================
    // 🔹 동아리 관련
    // ==========================
    public static final String APPLICATIONS = API_V1 + "/applications";
    public static final String CLUB_APPLICATIONS = API_V1 + "/clubs/{clubId}/applications";
    public static final String DEPARTMENT_CLUBS = API_V1 + "/departments/{departmentId}/clubs";

    // ==========================
    // 🔹 위시리스트 (찜)
    // ==========================
    public static final String WISHLIST = API_V1 + "/wishlist";
    public static final String WISHLISTS = API_V1 + "/wishlists"; // 복수형 (신규)
    public static final String WISHLIST_GENERAL_CATEGORY = WISHLIST + "/general/{category}";
    public static final String WISHLIST_MAJOR_DEPARTMENT = WISHLIST + "/major/{department}";

    // ==========================
    // 🔹 게시글 / 기타
    // ==========================
    public static final String POST_PATH = API_V1 + "/posts";

    // ==========================
    // 🔹 H2 콘솔 (환경 설정)
    // ==========================
    public static String H2_PATH;

    @Value("${spring.h2.console.path:/h2-console}")
    private String h2Path;

    @PostConstruct
    public void init() {
        H2_PATH = h2Path;
    }
}
