// (선택) 부가 응답: 학과별 클럽 목록 요약
// src/main/java/com/JoinUs/dp/global/domain/application/dto/ClubSummary.java
package com.JoinUs.dp.dto;

public class ClubSummary {
    private Long clubId;
    private String clubName;

    public ClubSummary() {}
    public ClubSummary(Long clubId, String clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }
    public Long getClubId() { return clubId; }
    public String getClubName() { return clubName; }
    public void setClubId(Long clubId) { this.clubId = clubId; }
    public void setClubName(String clubName) { this.clubName = clubName; }
}
