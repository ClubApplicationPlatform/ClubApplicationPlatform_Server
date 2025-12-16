package com.JoinUs.dp.controller;

import com.JoinUs.dp.common.exception.NotFoundException;
import com.JoinUs.dp.dto.QuestionCreateRequest;
import com.JoinUs.dp.dto.QuestionUpdateRequest;
import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.entity.ClubQuestion;
import com.JoinUs.dp.repository.ClubRepository;
import com.JoinUs.dp.service.ClubQuestionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clubs/{clubId}/questions")
@RequiredArgsConstructor
public class ClubQuestionController {

    private final ClubQuestionService clubQuestionService;
    private final ClubRepository clubRepository;

    /** 동아리 지원 질문 목록 조회 */
    @GetMapping
    public List<QuestionResponse> getQuestions(@PathVariable Long clubId) {

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NotFoundException("해당 clubId는 존재하지 않습니다. clubId=" + clubId));

        List<ClubQuestion> questions = clubQuestionService.getQuestionsByClub(club);

        return questions.stream()
                .map(q -> new QuestionResponse(
                        q.getId(),
                        q.getQuestion(),
                        q.getMaxLength() != null ? q.getMaxLength() : 1000
                ))
                .collect(Collectors.toList());
    }

    /** 질문 추가 */
    @PostMapping
    public QuestionResponse addQuestion(
            @PathVariable Long clubId,
            @RequestBody QuestionCreateRequest req
    ) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NotFoundException("해당 clubId는 존재하지 않습니다. clubId=" + clubId));

        ClubQuestion saved = clubQuestionService.addQuestion(
                club,
                req.getQuestion(),
                req.getMaxLength()
        );

        return new QuestionResponse(saved.getId(), saved.getQuestion(), saved.getMaxLength());
    }

    /** 질문 수정 */
    @PutMapping("/{questionId}")
    public QuestionResponse updateQuestion(
            @PathVariable Long clubId,
            @PathVariable Long questionId,
            @RequestBody QuestionUpdateRequest req
    ) {

        if (!clubRepository.existsById(clubId)) {
            throw new NotFoundException("해당 clubId는 존재하지 않습니다. clubId=" + clubId);
        }

        ClubQuestion updated = clubQuestionService.updateQuestion(
                questionId,
                req.getQuestion(),
                req.getMaxLength()
        );

        return new QuestionResponse(updated.getId(), updated.getQuestion(), updated.getMaxLength());
    }

    /** 질문 삭제(soft) */
    @DeleteMapping("/{questionId}")
    public void softDelete(@PathVariable Long clubId, @PathVariable Long questionId) {

        if (!clubRepository.existsById(clubId)) {
            throw new NotFoundException("해당 clubId는 존재하지 않습니다. clubId=" + clubId);
        }

        clubQuestionService.softDeleteQuestion(questionId);
    }

    @Data
    @AllArgsConstructor
    private static class QuestionResponse {
        private Long id;
        private String question;
        private int maxLength;
    }
}
