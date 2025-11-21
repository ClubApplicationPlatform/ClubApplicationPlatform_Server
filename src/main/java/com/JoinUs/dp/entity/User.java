package com.JoinUs.dp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false)
    private String department;

    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER; // DB 기본값이 USER

    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
