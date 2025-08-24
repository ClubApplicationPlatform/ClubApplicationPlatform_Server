package com.JoinUs.dp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 기본 PK

    @Column(nullable = false, unique = true)
    private String username; // 아이디 (로그인용)

    @Column(nullable = false)
    private String password; // 비밀번호 (암호화 필요)

    @Column(nullable = false)
    private String name;     // 이름

    @Column(nullable = false)
    private String department; // 학과

    @Column(nullable = false)
    private int grade; // 학년

    @Column(nullable = false, unique = true)
    private String email;    // 이메일
}
