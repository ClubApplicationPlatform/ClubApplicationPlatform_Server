package com.JoinUs.dp.domain.club.controller;

import com.JoinUs.dp.domain.club.dto.request.ClubCreateRequest;
import com.JoinUs.dp.domain.club.dto.request.ClubQuestionRequest;
import com.JoinUs.dp.domain.club.dto.request.RecruitStatusUpdateRequest;
import com.JoinUs.dp.domain.club.dto.response.ClubDetailResponse;
import com.JoinUs.dp.domain.club.dto.response.ClubSimpleResponse;
import com.JoinUs.dp.domain.club.service.ClubService;
import com.JoinUs.dp.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    /**
     * 동아리 생성 - 로그인한 사용자만 가능
     */
    @PostMapping
    public ResponseEntity<?> createClub(
            @RequestBody ClubCreateRequest dto,
            @RequestAttribute("user") User user
    ) {
        try {

            Long clubId = clubService.createClub(dto, user);  // ✅ 수정된 부분

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("clubId", clubId);
            response.put("status", "pending");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("success", false, "message", e.getMessage()));
        }
    }

    // POST /api/clubs/test
    @PostMapping("/test")
    public ResponseEntity<?> createClubWithoutUser(
            @RequestParam String name,
            @RequestParam String shortDesc,
            @RequestParam String description,
            @RequestParam String activities,
            @RequestParam String vision,
            @RequestParam MultipartFile pdfFile // 파일도 그대로 검증
    ) {
        try {
            ClubCreateRequest request = new ClubCreateRequest();
            request.setName(name);
            request.setShortDesc(shortDesc);
            request.setDescription(description);
            request.setActivities(activities);
            request.setVision(vision);

            // ✅ 유저 없이 저장되는 테스트용 서비스 메서드 호출
            Long clubId = clubService.createClubWithoutUser(request, pdfFile);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "clubId", clubId,
                    "status", "pending"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * 동아리 전체 조회 (type 파라미터: major / general)
     */
    @GetMapping
    public ResponseEntity<List<ClubSimpleResponse>> getClubs(
            @RequestParam(value = "type", required = false) String type) {
        List<ClubSimpleResponse> result = clubService.getClubList(type);
        return ResponseEntity.ok(result);
    }

    /**
     * 동아리 상세 조회
     */
    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDetailResponse> getClubDetail(@PathVariable Long clubId) {
        ClubDetailResponse response = clubService.getClubDetail(clubId);
        return ResponseEntity.ok(response);
    }



    /**
     * 모집 상태 변경
     */
    @PatchMapping("/{clubId}/recruit-status")
    public ResponseEntity<Map<String, Object>> updateRecruitStatus(
            @PathVariable Long clubId,
            @RequestBody RecruitStatusUpdateRequest request
    ) {
        clubService.updateRecruitStatus(clubId, request);
        return ResponseEntity.ok(Map.of("success", true));
    }

    /**
     * 이미지
     */
    @PostMapping("/{clubId}/image")
    public ResponseEntity<Map<String, Object>> uploadClubImage(
            @PathVariable Long clubId,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        clubService.uploadImage(clubId, image);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
