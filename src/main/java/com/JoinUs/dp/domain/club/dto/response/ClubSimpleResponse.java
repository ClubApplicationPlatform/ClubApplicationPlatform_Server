package com.JoinUs.dp.domain.club.dto.response;

import com.JoinUs.dp.domain.club.entity.Club;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClubSimpleResponse {
    private Long clubId;
    private String name;
    private String type;
    private String department;
    private String category;
    private String recruitStatus;
    private String imageUrl;

    public static ClubSimpleResponse from(Club club) {
        return ClubSimpleResponse.builder()
                .clubId(club.getId())
                .name(club.getName())
                .type(club.getType())
                .department(club.getDepartment())
                .category(club.getCategory())
                .recruitStatus(club.getRecruitStatus())
                .imageUrl(club.getImageUrl())
                .build();
    }
}