package com.project.domain.auth.repository;

import com.project.domain.auth.entity.LikedClub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedClubRepository extends JpaRepository<LikedClub, Long> {
}
