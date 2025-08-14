package com.JoinUs.dp.repository;

import com.JoinUs.dp.entity.ClubSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClubSearchRepository extends JpaRepository<ClubSearch, Long> {

    List<ClubSearch> findByNameContainingIgnoreCase(String name);
    List<ClubSearch> findByNameContainingIgnoreCaseAndMemberCountGreaterThanEqual(String name, Integer memberCount);
    List<ClubSearch> findByNameContainingIgnoreCaseAndRecruiting(String name, Boolean recruiting);
    List<ClubSearch> findByNameContainingIgnoreCaseAndMemberCountGreaterThanEqualAndRecruiting(String name, Integer memberCount, Boolean recruiting);
}
