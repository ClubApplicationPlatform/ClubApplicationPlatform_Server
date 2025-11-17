package com.JoinUs.dp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "club_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private String imageUrl;

    @Column(insertable = false, updatable = false)
    private String uploadedAt;
}
