package com.JoinUs.dp.repository;

import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.entity.ClubQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubQuestionRepository extends JpaRepository<ClubQuestion, Long> {

    // Club + active=1 조건으로 질문 조회
    List<ClubQuestion> findByClubAndActive(Club club, Integer active);
}
