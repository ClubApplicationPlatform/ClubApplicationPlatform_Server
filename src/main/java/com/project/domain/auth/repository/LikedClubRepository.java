package com.project.domain.auth.repository;

import com.project.domain.auth.entity.LikedClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedClubRepository extends JpaRepository<LikedClub, Long> {
    List<LikedClub> findByType(String type);
    List<LikedClub> findByTypeAndCategory(String type, String category);
    List<LikedClub> findByTypeAndDepartment(String type, String department);
}
