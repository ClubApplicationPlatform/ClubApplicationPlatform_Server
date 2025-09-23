// src/main/java/com/JoinUs/dp/global/domain/application/controller/ClubApplicationController.java
package com.JoinUs.dp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.JoinUs.dp.common.api.ApiPath;
import com.JoinUs.dp.common.response.Response;
import com.JoinUs.dp.dto.ClubApplicationDto;
import com.JoinUs.dp.dto.ClubSummary;
import com.JoinUs.dp.service.ClubApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ClubApplicationController {

    private final ClubApplicationService service;

    public ClubApplicationController(ClubApplicationService service) {
        this.service = service;
    }

    // 동아리 신청 등록: POST /api/applications
    @PostMapping(ApiPath.APPLICATIONS)
    public ResponseEntity<Response<ClubApplicationDto>> apply(@RequestBody ClubApplicationDto req) {
        ClubApplicationDto saved = service.apply(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(HttpStatus.CREATED.value(), saved, "동아리 신청 등록 완료"));
    }

    // 신청 목록 전체 조회: GET /api/applications
    @GetMapping(ApiPath.APPLICATIONS)
    public ResponseEntity<Response<List<ClubApplicationDto>>> findAll() {
        List<ClubApplicationDto> list = service.findAll();
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), list, "신청 목록 조회 성공"));
    }

    // 신청 상세 조회: GET /api/applications/{applicationId}
    @GetMapping(ApiPath.APPLICATIONS + "/{applicationId}")
    public ResponseEntity<Response<ClubApplicationDto>> findById(@PathVariable("applicationId") Long applicationId) {
        ClubApplicationDto dto = service.findById(applicationId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), dto, "신청 상세 조회 성공"));
    }

    // 신청 취소(삭제): DELETE /api/applications/{applicationId}
    @DeleteMapping(ApiPath.APPLICATIONS + "/{applicationId}")
    public ResponseEntity<Response<Void>> cancel(@PathVariable("applicationId") Long applicationId) {
        service.cancel(applicationId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), null, "신청 취소(삭제) 완료"));
    }

    // 신청 정보 수정(전체): PUT /api/applications
    @PutMapping(ApiPath.APPLICATIONS)
    public ResponseEntity<Response<ClubApplicationDto>> update(@RequestBody ClubApplicationDto req) {
        ClubApplicationDto updated = service.update(req);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), updated, "신청 정보 수정 완료"));
    }

    // 신청 정보 일부 수정: PATCH /api/applications/{applicationId}
    @PatchMapping(ApiPath.APPLICATIONS + "/{applicationId}")
    public ResponseEntity<Response<ClubApplicationDto>> partialUpdate(
            @PathVariable("applicationId") Long applicationId,
            @RequestBody Map<String, Object> updates) {
        ClubApplicationDto updated = service.partialUpdate(applicationId, updates);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), updated, "신청 정보 일부 수정 완료"));
    }

    // 특정 클럽의 신청자 목록 조회: GET /api/clubs/{clubId}/applications
    @GetMapping(ApiPath.CLUB_APPLICATIONS)
    public ResponseEntity<Response<List<ClubApplicationDto>>> findByClubId(@PathVariable("clubId") Long clubId) {
        List<ClubApplicationDto> list = service.findByClubId(clubId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), list, "클럽별 신청자 목록 조회 성공"));
    }

    // 합격 여부 설정(동아리장 전용): PATCH /api/applications/{applicationId}/result
    @PatchMapping(ApiPath.APPLICATIONS + "/{applicationId}/result")
    public ResponseEntity<Response<ClubApplicationDto>> setResult(
            @PathVariable("applicationId") Long applicationId,
            @RequestBody Map<String, String> body) {

        String result = body.get("result");
        String message = body.get("message");
        ClubApplicationDto updated = service.setResult(applicationId, result, message);

        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), updated, "합격 여부 설정 완료"));
    }

    // 합격자 최종 확정/철회(본인): PATCH /api/applications/{applicationId}/confirm
    @PatchMapping(ApiPath.APPLICATIONS + "/{applicationId}/confirm")
    public ResponseEntity<Response<ClubApplicationDto>> confirmOrDecline(
            @PathVariable("applicationId") Long applicationId,
            @RequestBody Map<String, String> body) {

        String action = body.get("action");
        ClubApplicationDto updated = service.confirmOrDecline(applicationId, action);

        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), updated, "확정/철회 처리 완료"));
    }

    // 불합격자 추가 합격 통보(동아리장 전용): PATCH /api/applications/{applicationId}/additional-offer
    @PatchMapping(ApiPath.APPLICATIONS + "/{applicationId}/additional-offer")
    public ResponseEntity<Response<ClubApplicationDto>> additionalOffer(@PathVariable("applicationId") Long applicationId) {
        ClubApplicationDto updated = service.additionalOffer(applicationId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), updated, "추가 합격 통보 완료"));
    }

    // 학과별 동아리 목록 조회: GET /api/departments/{departmentId}/clubs
    @GetMapping(ApiPath.DEPARTMENT_CLUBS)
    public ResponseEntity<Response<List<ClubSummary>>> getClubsByDepartment(@PathVariable("departmentId") Long departmentId) {
        List<ClubSummary> list = service.getClubsByDepartment(departmentId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), list, "학과별 클럽 목록 조회 성공"));
    }
}
