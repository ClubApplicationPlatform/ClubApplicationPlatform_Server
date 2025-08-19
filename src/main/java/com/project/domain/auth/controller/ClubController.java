package com.project.domain.auth.controller;

import com.project.domain.auth.entity.LikedClub;
import com.project.domain.auth.repository.LikedClubRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ClubController {

    private final LikedClubRepository likedClubRepository;

    public ClubController(LikedClubRepository likedClubRepository) {
        this.likedClubRepository = likedClubRepository;
    }

    // 기본 화면 (club.html 렌더링)
    @GetMapping("/")
    public String index() {
        return "club";
    }

    // 찜하기 (AJAX 요청 처리)
    @PostMapping("/clubs/like")
    @ResponseBody
    public String likeClub(@RequestBody Map<String, String> payload) {
        String clubName = payload.get("name");
        LikedClub likedClub = new LikedClub(clubName);
        likedClubRepository.save(likedClub);
        return clubName + " 찜 완료!";
    }
}
