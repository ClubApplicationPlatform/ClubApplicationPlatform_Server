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
}
