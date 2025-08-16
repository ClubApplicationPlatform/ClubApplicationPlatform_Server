package com.JoinUs.dp.domain.club.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor  // ✅ Builder에 필요한 전체 생성자 생성
@Builder
public class ClubCreateRequest {

    private String name;
    private String shortDesc;
    private String description;
    private String activities;
    private String vision;

    private String type;
    private String department;
    private String category;

    private MultipartFile pdfFile;
}
