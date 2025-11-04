package com.project.domain.auth.dto;

import com.project.domain.stubs.Club;
import lombok.Getter;

@Getter
public class WishlistResponse {
    private Long clubId; 
    private String name;
    private String type; 
    private String category;
    private String department;

    public WishlistResponse(Club club) {
        this.clubId = club.getId();
        this.name = club.getName();
        this.type = club.getType();
        this.category = club.getCategory();
        this.department = club.getDepartment();
    }
}