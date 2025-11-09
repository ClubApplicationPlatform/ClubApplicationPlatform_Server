package com.JoinUs.dp.domain.interview.entity;


import com.JoinUs.dp.domain.interview.entity.enums.InterviewConfirmedEnum;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewResultEnum;
import com.JoinUs.dp.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "InterviewApplications")
@Getter @Setter
public class Applicant {
    protected Applicant() {}

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "interview_id", nullable = false)
    private Long interviewId;

    @CreationTimestamp
    @Column(name = "applied_at", columnDefinition = "timestamp default current_timestamp", nullable = true, updatable = false)
    private LocalDateTime appliedAt;


    // 테이블 필드에 안적혀있었지만 신청자 면접 현황 조회, 미확정 인원 조회 API 구현을 위해 그대로 놔둠
    @Column
    private InterviewResultEnum interviewResult; // 면접 현황 (PENDING, PASSED, FAILED)
    @Column
    private InterviewConfirmedEnum confirmStatus; // 가입 확정 여부 (PENDING, CONFIRMED, DECLINED)
    @Column(name = "club_id", nullable = false)
    private Long clubId;

    // 생성자
    // interviewResult 는 PENDING(대기), confirmStatus 는 PENDING 로 초기화 됨
    public Applicant(
            Long userId,
            Long interviewId,
            Long clubId
        ) {
        this.interviewId = interviewId;
        this.userId = userId;
        this.clubId = clubId;


        this.interviewResult = InterviewResultEnum.PENDING;
        this.confirmStatus = InterviewConfirmedEnum.PENDING;
    }


}
