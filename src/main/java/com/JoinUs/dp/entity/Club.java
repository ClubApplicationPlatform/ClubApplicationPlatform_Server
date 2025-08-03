package com.JoinUs.dp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;             // 클럽 이름
    private int memberCount;         // 인원 수
    private boolean recruiting;      // 모집 여부 (true: 모집중, false: 모집중 아님)
}
