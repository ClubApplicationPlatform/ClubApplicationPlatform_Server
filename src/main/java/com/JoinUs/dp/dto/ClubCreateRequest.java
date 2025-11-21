package com.JoinUs.dp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubCreateRequest {
    private String name;
    private String shortDesc;
    private String description;
    private String type;       // general / major
    private String department;
    private String category;
    private Long leaderId;
}
