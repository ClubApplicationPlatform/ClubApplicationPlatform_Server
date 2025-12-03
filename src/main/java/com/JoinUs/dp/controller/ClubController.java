package com.JoinUs.dp.controller;

import com.JoinUs.dp.dto.ClubDetailResponse;
import com.JoinUs.dp.dto.ClubListResponse;
import com.JoinUs.dp.dto.RecruitUpdateRequest;
import com.JoinUs.dp.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    /** 전체 조회 (프론트에서 쓰는 형태로) */
    @GetMapping
    public ResponseEntity<List<ClubListResponse>> findAll() {
        return ResponseEntity.ok(clubService.findAllClubs());
    }

    /**
     * 단일 조회
     * - 기존 ClubDetailResponse 대신
     * - 리스트와 동일한 구조인 ClubListResponse로 반환
     */
    @GetMapping("/{clubId}")
    public ResponseEntity<ClubListResponse> findOne(@PathVariable Long clubId) {
        return ResponseEntity.ok(clubService.getClubFull(clubId));
    }

    /** 대표 이미지 업로드 (파일 형태로 업로드) */
    @PostMapping("/{clubId}/image")
    public ResponseEntity<Long> uploadImage(
            @PathVariable Long clubId,
            @RequestParam MultipartFile file) {

        Long id = clubService.uploadClubImage(clubId, file);
        return ResponseEntity.ok(id);
    }

    /** 모집 상태 + 마감일 변경 (통합 API) */
    @PatchMapping("/{clubId}/recruitment")
    public ResponseEntity<Void> updateRecruitment(
            @PathVariable Long clubId,
            @RequestBody RecruitUpdateRequest req) {

        clubService.updateRecruitment(clubId, req.getIsRecruiting(), req.getRecruitDeadline());
        return ResponseEntity.ok().build();
    }
}
