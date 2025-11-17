package com.JoinUs.dp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClubListResponse {

    private Long clubId;
    private String name;
    private String shortDesc;
    private String type;
    private String category;
    private String department;
    private String recruitStatus;
}
