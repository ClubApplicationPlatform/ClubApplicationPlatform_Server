package com.JoinUs.dp.domain.interview.entity;


import com.JoinUs.dp.domain.club.entity.Club;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Interview {
    protected Interview() {}

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Club club;

//    @Column
//    private Long clubId; // 어느 동아리에 속하는지 확인용

    @Column
    private LocalDateTime startAt; // 면접 시작 시간 (yyyy-MM-ddTHH:mm)
    @Column
    private LocalDateTime endAt; // 면접 종료 시간 (yyyy-MM-ddTHH:mm)
    @Column
    private String location; // 면접 장소

    @Column
    private Long capacity; // 면접 가능 인원
    @Column
    private Long applied; // 현재까지 신청 인원

    // 생성자
    // applied 는 0으로 초기화됨
    public Interview(
            Club club,
            LocalDateTime startAt,
            LocalDateTime endAt,
            String location,
            Long capacity
    ) {
        this.club = club;
        this.startAt = startAt;
        this.endAt = endAt;
        this.location = location;
        this.capacity = capacity;
        this.applied = 0L;
    }

    // 신청 인원 1 증가
    public void increaseApplied() {
        this.applied = this.applied + 1;
    }
}
