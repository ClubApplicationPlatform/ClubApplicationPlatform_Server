package com.JoinUs.dp.domain.interview.entity;


import com.JoinUs.dp.domain.club.entity.Club;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Interviews")
@Getter @Setter
public class Interview {
    protected Interview() {}

    @Id
    @GeneratedValue
    @Column(name = "interview_id", nullable = false)
    private Long id;

    @Column(name = "club_id", nullable = false)
    private Long clubId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "max_applicants", nullable = false)
    private int maxApplicants;


    // 테이블 필드에 없던거지만 필요해보여서 넣은거
    @Column
    private int applied;
    @Column
    private String location;



    // 생성자
    // applied 는 0으로 초기화됨
    public Interview(
            Long clubId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            int duration,
            int maxApplicants,
            
            String location
    ) {
        this.clubId = clubId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.maxApplicants = maxApplicants;

        this.applied = 0;
        this.location = location;
    }

    // 신청 인원 1 증가
    public void increaseApplied() {
        this.applied = this.applied + 1;
    }
}
