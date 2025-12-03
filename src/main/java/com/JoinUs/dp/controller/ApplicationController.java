package com.JoinUs.dp.controller;

import java.util.List;
import java.util.Map;

import com.JoinUs.dp.common.exception.BadRequestException;
import com.JoinUs.dp.common.response.Response;
import com.JoinUs.dp.dto.ApplicationDto;
import com.JoinUs.dp.dto.ClubSummary;
import com.JoinUs.dp.global.common.api.ApiPath;
import com.JoinUs.dp.service.ApplicationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService service;

    /** 신청 등록 */
    @PostMapping(ApiPath.APPLICATIONS)
    public ResponseEntity<Response<ApplicationDto>> apply(@RequestBody ApplicationDto req) {
        ApplicationDto saved = service.apply(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(201, saved, "동아리 신청 등록 완료"));
    }

    /** 신청 목록 조회 - userId/clubId 중 하나는 필수 */
    @GetMapping(ApiPath.APPLICATIONS)
    public ResponseEntity<Response<List<ApplicationDto>>> findAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long clubId
    ) {

        if (userId == null && clubId == null) {
            throw new BadRequestException("userId 또는 clubId는 반드시 포함해야 합니다.");
        }

        List<ApplicationDto> list =
                (userId != null) ? service.findByUserId(userId) : service.findByClubId(clubId);

        return ResponseEntity.ok(new Response<>(200, list, "신청 목록 조회 성공"));
    }

    /** 신청 상세 조회 */
    @GetMapping(ApiPath.APPLICATIONS + "/{applicationId}")
    public ResponseEntity<Response<ApplicationDto>> findById(@PathVariable Long applicationId) {
        ApplicationDto dto = service.findById(applicationId);
        return ResponseEntity.ok(new Response<>(200, dto, "신청 상세 조회 성공"));
    }

    /** 신청 취소 */
    @DeleteMapping(ApiPath.APPLICATIONS + "/{applicationId}")
    public ResponseEntity<Response<Void>> cancel(@PathVariable Long applicationId) {
        service.cancel(applicationId);
        return ResponseEntity.ok(new Response<>(200, null, "신청 취소 완료"));
    }

    /** 신청 전체 수정 */
    @PutMapping(ApiPath.APPLICATIONS)
    public ResponseEntity<Response<ApplicationDto>> update(@RequestBody ApplicationDto req) {
        ApplicationDto updated = service.update(req);
        return ResponseEntity.ok(new Response<>(200, updated, "신청 수정 완료"));
    }

    /** 신청 부분 수정 */
    @PatchMapping(ApiPath.APPLICATIONS + "/{applicationId}")
    public ResponseEntity<Response<ApplicationDto>> partialUpdate(
            @PathVariable Long applicationId,
            @RequestBody Map<String, Object> updates) {

        ApplicationDto updated = service.partialUpdate(applicationId, updates);
        return ResponseEntity.ok(new Response<>(200, updated, "신청 부분 수정 완료"));
    }

    /** 클럽별 신청자 목록 조회 */
    @GetMapping(ApiPath.CLUB_APPLICATIONS)
    public ResponseEntity<Response<List<ApplicationDto>>> findByClubId(@PathVariable Long clubId) {
        List<ApplicationDto> list = service.findByClubId(clubId);
        return ResponseEntity.ok(new Response<>(200, list, "클럽별 신청자 목록 조회 성공"));
    }

    /** 결과 설정 */
    @PatchMapping(ApiPath.APPLICATIONS + "/{applicationId}/result")
    public ResponseEntity<Response<ApplicationDto>> setResult(
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> body) {

        ApplicationDto updated = service.setResult(
                applicationId,
                body.get("result"),
                body.get("message")
        );

        return ResponseEntity.ok(new Response<>(200, updated, "합격/불합격 설정 완료"));
    }

    /** 확정/철회 */
    @PatchMapping(ApiPath.APPLICATIONS + "/{applicationId}/confirm")
    public ResponseEntity<Response<ApplicationDto>> confirm(
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> body) {

        ApplicationDto updated = service.confirmOrDecline(
                applicationId,
                body.get("action")
        );

        return ResponseEntity.ok(new Response<>(200, updated, "확정/철회 처리 완료"));
    }

    /** 추가 합격 */
    @PatchMapping(ApiPath.APPLICATIONS + "/{applicationId}/additional-offer")
    public ResponseEntity<Response<ApplicationDto>> additional(@PathVariable Long applicationId) {
        ApplicationDto updated = service.additionalOffer(applicationId);
        return ResponseEntity.ok(new Response<>(200, updated, "추가 합격 통보 완료"));
    }

    /** 학과별 클럽 조회 */
    @GetMapping(ApiPath.DEPARTMENT_CLUBS)
    public ResponseEntity<Response<List<ClubSummary>>> findByDept(
            @PathVariable("departmentId") String departmentId
    ) {
        List<ClubSummary> list = service.getClubsByDepartment(departmentId);
        return ResponseEntity.ok(new Response<>(200, list, "학과별 클럽 목록 조회 성공"));
    }
}
