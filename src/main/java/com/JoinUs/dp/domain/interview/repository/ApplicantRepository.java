package com.JoinUs.dp.domain.interview.repository;

import com.JoinUs.dp.domain.interview.entity.Applicant;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewConfirmedEnum;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewResultEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    // 특정 유저가 특정 동아리에 이미 지원했는지 조회
    // 면접 중복 신청 방지에 사용됨
    boolean existsByUserIdAndClubId(Long userId, Long clubId);

    // 특정 면접 회차에 지원한 모든 유저(신청자) 목록
    // 면접 현황 조회 기능에 사용됨
    List<Applicant> findAllByInterviewId(Long interviewId);

    // 특정 동아리에 합격한 유저(신청자)들의 목록. 동아리Id, 합격여부, 확정여부 로 필터링 됨
    // 미확정/가입거절 인원 조회에 사용됨
    List<Applicant> findAllByClubIdAndInterviewResultAndConfirmStatus(
            Long clubId,
            InterviewResultEnum interviewResult,
            InterviewConfirmedEnum confirmStatus
    );

    // clubId 동아리에 신청한 유저 수 조회
    Long countByClubId(Long clubId);

    // 미확정/가입거절, 거절 인원수 조회
    Long countByClubIdAndInterviewResultAndConfirmStatus(
            Long clubId,
            InterviewResultEnum interviewResult,
            InterviewConfirmedEnum confirmStatus
    );


    // 테스트 데이터 생성용 코드(domain.interview.SetData 파일 삭제시 삭제해도됨)
//    Applicant findByUser_Username(String username);

}
