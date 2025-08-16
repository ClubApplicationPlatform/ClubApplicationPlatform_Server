package com.JoinUs.dp.domain.club.dto.response;

import com.JoinUs.dp.domain.club.entity.Club;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubDetailResponse {

    private Long clubId;
    private String name;
    private String shortDesc;
    private String description;
    private String activities;
    private String vision;
    private String type;
    private String department;
    private String category;
    private String recruitStatus;
    private String status;
    private String pdfFilePath;
    private String imageUrl;

    public static ClubDetailResponse from(Club club) {
        return ClubDetailResponse.builder()
                .clubId(club.getId())
                .name(club.getName())
                .shortDesc(club.getShortDesc())
                .description(club.getDescription())
                .activities(club.getActivities())
                .vision(club.getVision())
                .type(club.getType())
                .department(club.getDepartment())
                .category(club.getCategory())
                .recruitStatus(club.getRecruitStatus())
                .status(club.getStatus())
                .pdfFilePath(club.getPdfFilePath())
                .imageUrl(club.getImageUrl())
                .build();
    }
}