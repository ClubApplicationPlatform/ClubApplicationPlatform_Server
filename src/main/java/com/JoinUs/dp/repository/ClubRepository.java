package com.JoinUs.dp.repository;

import com.JoinUs.dp.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    // 이름 포함, 인원 이상, 모집 여부 조건 모두 만족하는 클럽 리스트 반환
    List<Club> findByNameContainingIgnoreCaseAndMemberCountGreaterThanEqualAndRecruiting(
            String name, int memberCount, boolean recruiting);

    // 이름 포함, 인원 이상 조건만 (모집여부 미지정)
    List<Club> findByNameContainingIgnoreCaseAndMemberCountGreaterThanEqual(
            String name, int memberCount);

    // 이름 포함, 모집여부만
    List<Club> findByNameContainingIgnoreCaseAndRecruiting(
            String name, boolean recruiting);

    // 이름만
    List<Club> findByNameContainingIgnoreCase(String name);
}
