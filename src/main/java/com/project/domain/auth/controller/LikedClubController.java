package com.project.domain.auth.controller;

import com.project.domain.global.constant.ApiPath;
import com.project.domain.global.dto.Response;
import com.project.domain.auth.dto.LikedClubRequest;
import com.project.domain.auth.dto.LikedClubResponse;
import com.project.domain.auth.entity.LikedClub;
import com.project.domain.auth.service.LikedClubService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ApiPath.LIKED_CLUBS)
@RequiredArgsConstructor
public class LikedClubController {

    private final LikedClubService likedClubService;

    @PostMapping
    public ResponseEntity<Response<LikedClubResponse>> likeClub(@Valid @RequestBody LikedClubRequest request) {
        LikedClub club = likedClubService.likeClub(request);
        LikedClubResponse responseDto = new LikedClubResponse(club);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, responseDto, club.getName() + " 찜 완료!"));
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Response<String>> unlikeClub(@PathVariable Long clubId) {
        likedClubService.unlikeClub(clubId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, null, "찜 삭제 완료!"));
    }

    @GetMapping
    public ResponseEntity<Response<List<LikedClubResponse>>> getClubs(@RequestParam(required = false) String type) {
        List<LikedClub> clubs = likedClubService.getAllClubs(type);
        List<LikedClubResponse> responses = clubs.stream()
                .map(LikedClubResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, responses, "전체 또는 필터링된 찜 목록"));
    }

    @GetMapping("/general/{category}")
    public ResponseEntity<Response<List<LikedClubResponse>>> getGeneral(@PathVariable String category) {
        List<LikedClub> clubs = likedClubService.getGeneralByCategory(category);
        List<LikedClubResponse> responses = clubs.stream()
                .map(LikedClubResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, responses, "일반동아리 필터링"));
    }

    @GetMapping("/major/{department}")
    public ResponseEntity<Response<List<LikedClubResponse>>> getMajor(@PathVariable String department) {
        List<LikedClub> clubs = likedClubService.getMajorByDepartment(department);
        List<LikedClubResponse> responses = clubs.stream()
                .map(LikedClubResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, responses, "전공동아리 필터링"));
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
