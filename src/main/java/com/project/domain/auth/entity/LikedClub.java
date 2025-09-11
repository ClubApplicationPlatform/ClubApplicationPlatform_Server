package com.project.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "liked_clubs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikedClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type; // "major" or "general"
    private String category;
    private String department;

    @Column(name = "notification_enabled")
    private boolean notificationEnabled = false;

    public LikedClub(String name, String type, String category, String department) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.department = department;
    }
}
