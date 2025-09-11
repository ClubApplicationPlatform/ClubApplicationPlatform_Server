package com.project.domain.auth.dto;

import com.project.domain.auth.entity.LikedClub;
import lombok.Getter;

@Getter
public class LikedClubResponse {
    private Long id;
    private String name;
    private String type;
    private String category;
    private String department;
    private boolean notificationEnabled;

    public LikedClubResponse(LikedClub club) {
        this.id = club.getId();
        this.name = club.getName();
        this.type = club.getType();
        this.category = club.getCategory();
        this.department = club.getDepartment();
        this.notificationEnabled = club.isNotificationEnabled();
    }
}
