// src/main/java/com/JoinUs/dp/service/ApplicationService.java
package com.JoinUs.dp.service;

import com.JoinUs.dp.common.exception.NotFoundException;
import com.JoinUs.dp.dto.ApplicationDto;
import com.JoinUs.dp.dto.ClubSummary;
import com.JoinUs.dp.entity.Application;
import com.JoinUs.dp.entity.ClubStatus;
import com.JoinUs.dp.repository.ApplicationRepository;
import com.JoinUs.dp.repository.ClubRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository repository;
    private final ClubRepository clubRepository;

    /* ===========================
       1. 신청 등록
       =========================== */

    @Transactional
    public ApplicationDto apply(ApplicationDto dto) {
        Application e = toEntity(dto);
        e.setId(null);                     // 새 신청
        e.setStatus(ClubStatus.PENDING);   // 기본값 설정

        return toDto(repository.save(e));
    }

    /* ===========================
       2. 전체 조회
       =========================== */
    public List<ApplicationDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /* ===========================
       3. 단건 조회
       =========================== */
    public ApplicationDto findById(Long id) {
        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));
        return toDto(e);
    }

    /* ===========================
       4. 삭제(취소)
       =========================== */
    @Transactional
    public void cancel(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("신청을 찾을 수 없습니다. id=" + id);
        }
        repository.deleteById(id);
    }

    /* ===========================
       5. 전체 수정
       =========================== */
    @Transactional
    public ApplicationDto update(ApplicationDto dto) {
        if (dto.getApplicationId() == null) {
            throw new IllegalArgumentException("applicationId는 필수입니다.");
        }

        Application e = repository.findById(dto.getApplicationId())
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + dto.getApplicationId()));

        e.setUserId(dto.getUserId());
        e.setClubId(dto.getClubId());
        e.setStatus(dto.getStatus());
        e.setMessage(dto.getMessage());

        return toDto(repository.save(e));
    }

    /* ===========================
       6. 부분 수정(PATCH)
       =========================== */
    @Transactional
    public ApplicationDto partialUpdate(Long id, Map<String, Object> updates) {

        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if (updates.containsKey("userId")) {
            e.setUserId(Long.valueOf(updates.get("userId").toString()));
        }
        if (updates.containsKey("clubId")) {
            e.setClubId(Long.valueOf(updates.get("clubId").toString()));
        }
        if (updates.containsKey("status")) {
            e.setStatus(ClubStatus.valueOf(updates.get("status").toString().toUpperCase()));
        }
        if (updates.containsKey("message")) {
            e.setMessage((String) updates.get("message"));
        }

        return toDto(repository.save(e));
    }

    /* ===========================
       7. 클럽별 신청 목록 조회
       =========================== */
    public List<ApplicationDto> findByClubId(Long clubId) {
        return repository.findByClubId(clubId).stream()
                .map(this::toDto)
                .toList();
    }

    /* ===========================
       8. 합격/불합격 설정
       =========================== */
    @Transactional
    public ApplicationDto setResult(Long id, String result, String message) {

        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if ("passed".equalsIgnoreCase(result)) {
            e.setStatus(ClubStatus.PASSED);
        } else if ("failed".equalsIgnoreCase(result)) {
            e.setStatus(ClubStatus.FAILED);
        } else {
            throw new IllegalArgumentException("result 값은 passed 또는 failed 이어야 합니다.");
        }

        e.setMessage(message);
        return toDto(repository.save(e));
    }

    /* ===========================
       9. 합격자 확정/철회
       =========================== */
    @Transactional
    public ApplicationDto confirmOrDecline(Long id, String action) {

        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if (!(e.getStatus() == ClubStatus.PASSED || e.getStatus() == ClubStatus.CONFIRMED)) {
            throw new IllegalStateException("현재 상태에서 확정/철회할 수 없습니다. status=" + e.getStatus());
        }

        if ("confirm".equalsIgnoreCase(action)) {
            e.setStatus(ClubStatus.CONFIRMED);
        } else if ("decline".equalsIgnoreCase(action)) {
            e.setStatus(ClubStatus.DECLINED);
        } else {
            throw new IllegalArgumentException("action 값은 confirm 또는 decline 이어야 합니다.");
        }

        return toDto(repository.save(e));
    }

    /* ===========================
       10. 추가 합격
       =========================== */
    @Transactional
    public ApplicationDto additionalOffer(Long id) {

        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if (e.getStatus() != ClubStatus.FAILED) {
            throw new IllegalStateException("추가 합격 통보는 FAILED 상태에서만 가능합니다.");
        }

        e.setStatus(ClubStatus.PASSED);
        e.setMessage("추가 합격 통보");

        return toDto(repository.save(e));
    }

    /* ===========================
       11. 학과별 클럽 조회 (옵션)
       =========================== */
    public List<ClubSummary> getClubsByDepartment(String department) {
        return clubRepository.findByDepartment(department)
                .stream()
                .map(c -> new ClubSummary(c.getClubId(), c.getName()))
                .toList();
    }

    /* ===========================
       내부 변환 메서드
       =========================== */

    private ApplicationDto toDto(Application e) {
        return ApplicationDto.from(e);
    }

    private Application toEntity(ApplicationDto dto) {
        Application e = new Application();
        e.setId(dto.getApplicationId());
        e.setUserId(dto.getUserId());
        e.setClubId(dto.getClubId());
        e.setStatus(dto.getStatus());
        e.setMessage(dto.getMessage());
        return e;
    }
}
