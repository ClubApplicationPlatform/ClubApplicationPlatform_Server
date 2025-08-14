package com.JoinUs.dp.controller;

import com.JoinUs.dp.entity.ClubQuestion;
import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.service.ClubQuestionService;
import com.JoinUs.dp.repository.ClubSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs/{clubId}/questions")
public class ClubQuestionController {

    private final ClubQuestionService clubQuestionService;
    private final ClubSearchRepository clubSearchRepository;

    @GetMapping
    public List<ClubQuestion> getQuestions(@PathVariable Long clubId) {
        ClubSearch club = clubSearchRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("동아리를 찾을 수 없습니다."));
        return clubQuestionService.getQuestionsByClub(club);
    }

    @PostMapping
    public ClubQuestion addQuestion(@PathVariable Long clubId, @RequestParam String question) {
        ClubSearch club = clubSearchRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("동아리를 찾을 수 없습니다."));
        return clubQuestionService.addQuestion(club, question);
    }

    @PutMapping("/{questionId}/answer")
    public ClubQuestion updateAnswer(@PathVariable Long questionId, @RequestParam String answer) {
        return clubQuestionService.updateAnswer(questionId, answer);
    }

    @DeleteMapping("/{questionId}")
    public void softDeleteQuestion(@PathVariable Long questionId) {
        clubQuestionService.softDeleteQuestion(questionId);
    }
}
