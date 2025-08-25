package com.project.domain.auth.controller;

import com.project.domain.auth.entity.LikedClub;
import com.project.domain.auth.repository.LikedClubRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ClubController {

    private final LikedClubRepository likedClubRepository;

    public ClubController(LikedClubRepository likedClubRepository) {
        this.likedClubRepository = likedClubRepository;
    }

    // 기본 화면 (club.html 렌더링) - 만약 Thymeleaf 사용 시
    // @Controller + @GetMapping("/") 도 가능
    @GetMapping("/")
    public String index() {
        return "club";
    }

    // 찜하기 (AJAX 요청 처리)
    @PostMapping("/clubs/like")
    public String likeClub(@RequestBody Map<String, String> payload) {
        String clubName = payload.get("name");
        LikedClub likedClub = new LikedClub(clubName, null, null, null);
        likedClubRepository.save(likedClub);
        return clubName + " 찜 완료!";
    }
}
