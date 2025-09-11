package com.project.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikedClubRequest {
    private String name;
    private String type;
    private String category;
    private String department;
}
