// src/main/java/com/JoinUs/dp/global/common/api/ApiPath.java
package com.JoinUs.dp.common.api;

public final class ApiPath {
    private ApiPath() {}

    // 공지/FAQ
    public static final String NOTICE_PATH = "/api/notices";
    public static final String CLUB_NOTICE_PATH = "/api/clubs/{clubId}/notice";

    // ✅ 동아리 신청
    public static final String APPLICATIONS = "/api/applications";
    public static final String CLUB_APPLICATIONS = "/api/clubs/{clubId}/applications";
    public static final String DEPARTMENT_CLUBS = "/api/departments/{departmentId}/clubs";
    
    
    // 공통 API 기본 경로
    public static final String API_BASE = "/api";

    // Auth 관련 API 경로
    public static final String AUTH = API_BASE + "/auth";

    // Wishlist 관련 API 경로 (LIKED_CLUBS -> WISHLISTS로 변경) << 요기 수정 사항
    public static final String WISHLISTS = API_BASE + "/wishlists"; 

    // User 관련 API 경로
    public static final String USER = API_BASE + "/users";

    // Post 관련 API 경로
    public static final String POST = API_BASE + "/posts";

    // 그 외 필요한 경로 추가 가능
}
