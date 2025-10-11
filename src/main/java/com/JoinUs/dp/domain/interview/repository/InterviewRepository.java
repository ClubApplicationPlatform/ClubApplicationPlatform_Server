package com.JoinUs.dp.domain.interview.repository;

import com.JoinUs.dp.domain.interview.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    // clubId 로 모든 면접 회차 조회
    // 면접 회차 조회 및 면접 현황 조회 기능에 사용됨
    List<Interview> findAllByClub_Id(Long clubId);
}
