package com.project.domain.auth.controller;

import com.project.domain.global.dto.Response;
import com.project.domain.auth.dto.LikedClubRequest;
import com.project.domain.auth.entity.LikedClub;
import com.project.domain.auth.service.LikedClubService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liked-clubs")
@RequiredArgsConstructor
public class LikedClubController {

    private final LikedClubService likedClubService;

    @PostMapping
    public ResponseEntity<Response<LikedClub>> likeClub(@RequestBody LikedClubRequest request) {
        LikedClub club = likedClubService.likeClub(request);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, club, club.getName() + " 찜 완료!"));
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Response<String>> unlikeClub(@PathVariable Long clubId) {
        try {
            likedClubService.unlikeClub(clubId);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK, null, "찜 삭제 완료!"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>(HttpStatus.NOT_FOUND, null, e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<LikedClub>>> getClubs(@RequestParam(required = false) String type) {
        List<LikedClub> clubs = likedClubService.getAllClubs(type);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, clubs, "전체 또는 필터링된 찜 목록"));
    }

    @GetMapping("/general/{category}")
    public ResponseEntity<Response<List<LikedClub>>> getGeneral(@PathVariable String category) {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, likedClubService.getGeneralByCategory(category), "일반동아리 필터링"));
    }

    @GetMapping("/major/{department}")
    public ResponseEntity<Response<List<LikedClub>>> getMajor(@PathVariable String department) {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, likedClubService.getMajorByDepartment(department), "전공동아리 필터링"));
    }

    @PostMapping("/{clubId}/enable-notification")
    public ResponseEntity<Response<String>> enableNotification(@PathVariable Long clubId) {
        boolean updated = likedClubService.enableNotification(clubId);
        if (updated) {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK, null, "모집 알림 설정 완료!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response<>(HttpStatus.NOT_FOUND, null, "클럽을 찾을 수 없습니다."));
        }
    }
}
