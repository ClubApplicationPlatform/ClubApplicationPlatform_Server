package com.JoinUs.dp.domain.club.repository;

import com.JoinUs.dp.domain.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    // 전공/일반 구분
    List<Club> findByType(String type);

    // 전공 동아리 과별 조회
    List<Club> findByTypeAndDepartment(String type, String department);

    // 일반 동아리 카테고리별 조회
    List<Club> findByTypeAndCategory(String type, String category);
}
