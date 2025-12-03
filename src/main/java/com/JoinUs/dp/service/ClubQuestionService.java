package com.JoinUs.dp.service;

import com.JoinUs.dp.common.exception.NotFoundException;
import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.entity.ClubQuestion;
import com.JoinUs.dp.repository.ClubQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubQuestionService {

    private final ClubQuestionRepository clubQuestionRepository;

    /** 특정 클럽의 활성 질문 목록 */
    public List<ClubQuestion> getQuestionsByClub(Club club) {
        return clubQuestionRepository.findByClubAndActive(club, 1);
    }

    /** 질문 추가 */
    public ClubQuestion addQuestion(Club club, String questionText, Integer maxLength) {
        ClubQuestion q = new ClubQuestion();
        q.setClub(club);
        q.setQuestion(questionText);
        q.setMaxLength(maxLength != null ? maxLength : 1000);
        q.setActive(1);
        return clubQuestionRepository.save(q);
    }

    /** 질문 수정 */
    public ClubQuestion updateQuestion(Long questionId, String questionText, Integer maxLength) {
        ClubQuestion q = clubQuestionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("질문을 찾을 수 없습니다. id=" + questionId));

        if (questionText != null) q.setQuestion(questionText);
        if (maxLength != null) q.setMaxLength(maxLength);

        return clubQuestionRepository.save(q);
    }

    /** 질문 삭제 */
    public void softDeleteQuestion(Long questionId) {
        ClubQuestion q = clubQuestionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("질문을 찾을 수 없습니다. id=" + questionId));
        q.setActive(0);
        clubQuestionRepository.save(q);
    }
}
