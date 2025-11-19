package com.JoinUs.dp.controller;

import com.JoinUs.dp.dto.ClubCreateRequest;
import com.JoinUs.dp.service.ClubService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<?> createClub(@RequestBody ClubCreateRequest request) {
        return ResponseEntity.ok(clubService.createClub(request));
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<?> getClub(@PathVariable Long clubId) {
        return ResponseEntity.ok(clubService.getClubDetail(clubId));
    }

    @GetMapping
    public ResponseEntity<?> getClubs(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category
//            @RequestParam(required = false) Long department
    ) {
        if (type == null) return ResponseEntity.ok(clubService.findAllClubs());
        if (type != null && category != null) return ResponseEntity.ok(clubService.findByTypeAndCategory(type, category));
//        if (type != null && department != null) return ResponseEntity.ok(clubService.findByDepartment(department));
        return ResponseEntity.ok(clubService.findByType(type));
    }

    @PostMapping("/{clubId}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long clubId,
            @RequestParam MultipartFile file
    ) {
        return ResponseEntity.ok(clubService.uploadClubImage(clubId, file));
    }

    @PatchMapping("/{clubId}/recruit-status")
    public ResponseEntity<?> updateRecruitStatus(
            @PathVariable Long clubId,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(clubService.updateRecruitStatus(clubId, status));
    }

    @PatchMapping("/{clubId}/application-deadline")
    public ResponseEntity<?> updateDeadline(
            @PathVariable Long clubId,
            @RequestParam String endDate
    ) {
        return ResponseEntity.ok(clubService.updateDeadline(clubId, endDate));
    }

    @PatchMapping("/{clubId}/close")
    public ResponseEntity<?> closeRecruit(@PathVariable Long clubId) {
        return ResponseEntity.ok(clubService.closeRecruit(clubId));
    }
}
