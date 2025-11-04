package com.project.domain.global.constant;

public class ApiPath {

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