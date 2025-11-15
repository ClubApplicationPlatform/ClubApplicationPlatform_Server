package com.JoinUs.dp.repository;

import com.JoinUs.dp.entity.ClubQuestion;
import com.JoinUs.dp.entity.ClubSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClubQuestionRepository extends JpaRepository<ClubQuestion, Long> {
    List<ClubQuestion> findByClubAndActive(ClubSearch club, int active);
}
