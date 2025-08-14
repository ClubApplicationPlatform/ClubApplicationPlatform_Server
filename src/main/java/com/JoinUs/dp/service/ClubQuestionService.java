package com.JoinUs.dp.service;

import com.JoinUs.dp.entity.ClubQuestion;
import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.repository.ClubQuestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClubQuestionService {

    private final ClubQuestionRepository clubQuestionRepository;

    public ClubQuestionService(ClubQuestionRepository clubQuestionRepository) {
        this.clubQuestionRepository = clubQuestionRepository;
    }

    public List<ClubQuestion> getQuestionsByClub(ClubSearch club) {
        return clubQuestionRepository.findByClubAndActive(club, 1);
    }

    public ClubQuestion addQuestion(ClubSearch club, String questionText) {
        ClubQuestion question = new ClubQuestion();
        question.setClub(club);
        question.setQuestion(questionText);
        question.setActive(1);
        return clubQuestionRepository.save(question);
    }

    public ClubQuestion updateAnswer(Long questionId, String answer) {
        ClubQuestion q = clubQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다."));
        q.setAnswer(answer);
        return clubQuestionRepository.save(q);
    }

    public void softDeleteQuestion(Long questionId) {
        ClubQuestion q = clubQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("질문을 찾을 수 없습니다."));
        q.setActive(0);
        clubQuestionRepository.save(q);
    }
}
