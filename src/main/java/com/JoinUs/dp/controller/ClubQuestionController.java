package com.JoinUs.dp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.JoinUs.dp.dto.QuestionCreateRequest;
import com.JoinUs.dp.dto.AnswerUpdateRequest;
import com.JoinUs.dp.entity.ClubQuestion;
import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.repository.ClubSearchRepository;
import com.JoinUs.dp.service.ClubQuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs/{clubId}/questions")
public class ClubQuestionController {

    private final ClubQuestionService clubQuestionService;
    private final ClubSearchRepository clubSearchRepository;

    /** 질문 목록 조회 */
    @GetMapping
    public List<ClubQuestion> getQuestions(@PathVariable Long clubId) {
        ClubSearch club = clubSearchRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("동아리를 찾을 수 없습니다."));
        return clubQuestionService.getQuestionsByClub(club);
    }

    /** 질문 등록 (RequestBody로 받게 수정) */
    @PostMapping
    public ClubQuestion addQuestion(
            @PathVariable Long clubId,
            @RequestBody QuestionCreateRequest req
    ) {
        ClubSearch club = clubSearchRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("동아리를 찾을 수 없습니다."));
        return clubQuestionService.addQuestion(club, req.getQuestion());
    }

    /** 답변 등록/수정 (RequestBody로 받게 수정) */
    @PutMapping("/{questionId}/answer")
    public ClubQuestion updateAnswer(
            @PathVariable Long questionId,
            @RequestBody AnswerUpdateRequest req
    ) {
        return clubQuestionService.updateAnswer(questionId, req.getAnswer());
    }

    /** 질문 소프트 삭제 */
    @DeleteMapping("/{questionId}")
    public void softDeleteQuestion(@PathVariable Long questionId) {
        clubQuestionService.softDeleteQuestion(questionId);
    }
}
