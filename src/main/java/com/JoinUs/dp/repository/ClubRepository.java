package com.JoinUs.dp.repository;

import com.JoinUs.dp.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    // type(major/general)으로 동아리 필터링
    List<Club> findByType(String type);

    // 일반동아리 category 검색
    List<Club> findByTypeAndCategory(String type, String category);

    // 전공동아리 department 검색
    List<Club> findByTypeAndDepartment(String type, String department);
}
