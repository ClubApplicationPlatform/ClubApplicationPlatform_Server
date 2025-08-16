package com.JoinUs.dp.domain.club.entity;

import com.JoinUs.dp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leader_email")
    private User leader;

    private String name;
    private String shortDesc;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String activities;

    @Column(columnDefinition = "TEXT")
    private String vision;

    @Column(name = "image_url")
    private String imageUrl;

    // 동아리 유형: major / general
    @Column
    private String type;

    // 전공 동아리일 경우 학과명
    @Column
    private String department;

    // 일반 동아리일 경우 카테고리 (예: 운동, 봉사)
    @Column
    private String category;


    private String pdfFilePath;

    private String status;         // ex: "pending"
    private String recruitStatus;  // ex: "closed"

    private String recruitmentNotice;
    private LocalDate recruitmentStartDate;
    private LocalDate recruitmentEndDate;
    private String imagePath; // 이미지 파일 경로
}