package com.JoinUs.dp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JoinUs.dp.entity.ClubQuestion;
import com.JoinUs.dp.entity.ClubSearch;

public interface ClubQuestionRepository extends JpaRepository<ClubQuestion, Long> {
    List<ClubQuestion> findByClubAndActive(ClubSearch club, int active);
}
