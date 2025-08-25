package com.project.domain.auth.controller;

import com.project.domain.auth.entity.LikedClub;
import com.project.domain.auth.repository.LikedClubRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final LikedClubRepository likedClubRepository;

    public NotificationController(LikedClubRepository likedClubRepository) {
        this.likedClubRepository = likedClubRepository;
    }

    @PostMapping
    public ResponseEntity<String> enableNotification(@RequestBody Map<String, Long> payload) {
        Long clubId = payload.get("clubId");
        LikedClub club = likedClubRepository.findById(clubId).orElse(null);

        if (club == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("클럽을 찾을 수 없습니다.");
        }

        club.setNotificationEnabled(true);
        likedClubRepository.save(club);
        return ResponseEntity.ok("모집 알림 설정 완료!");
    }
}
