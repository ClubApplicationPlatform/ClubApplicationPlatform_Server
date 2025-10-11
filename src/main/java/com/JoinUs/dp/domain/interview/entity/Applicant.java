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
@Getter @Setter
public class Applicant {
    protected Applicant() {}

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Interview interview;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private User user;


    @Column
    private InterviewResultEnum interviewResult; // 면접 현황 (PENDING, PASSED, FAILED)
    @Column
    private InterviewConfirmedEnum confirmStatus; // 가입 확정 여부 (PENDING, CONFIRMED, DECLINED)

    // 생성자
    // interviewResult 는 PENDING(대기), confirmStatus 는 PENDING 로 초기화 됨
    public Applicant(
            Interview interview,
            User user
    ) {
        this.interview = interview;
        this.user = user;
        this.interviewResult = InterviewResultEnum.PENDING;
        this.confirmStatus = InterviewConfirmedEnum.PENDING;
    }


}
