package com.JoinUs.dp.service;

import com.JoinUs.dp.common.exception.NotFoundException;
import com.JoinUs.dp.dto.ApplicationDto;
import com.JoinUs.dp.dto.ClubSummary;
import com.JoinUs.dp.entity.Application;
import com.JoinUs.dp.entity.ClubStatus;
import com.JoinUs.dp.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository repository;

    // -------------------- 신청 등록 --------------------
    public ApplicationDto apply(ApplicationDto dto) {
        Application e = toEntity(dto);
        e.setId(null);
        e.setStatus(ClubStatus.PENDING);
        return toDto(repository.save(e));
    }

    // -------------------- 전체 조회 --------------------
    public List<ApplicationDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    // -------------------- 단건 조회 --------------------
    public ApplicationDto findById(Long id) {
        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));
        return toDto(e);
    }

    // -------------------- 신청 삭제 --------------------
    public void cancel(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("신청을 찾을 수 없습니다. id=" + id);
        }
        repository.deleteById(id);
    }

    // -------------------- 전체 수정 --------------------
    public ApplicationDto update(ApplicationDto dto) {

        Application e = repository.findById(dto.getApplicationId())
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + dto.getApplicationId()));

        e.setClubId(dto.getClubId());
        e.setStatus(dto.getStatus());
        e.setMessage(dto.getMessage());

        return toDto(repository.save(e));
    }

    // -------------------- 부분 수정 --------------------
    public ApplicationDto partialUpdate(Long id, Map<String, Object> updates) {

        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if (updates.containsKey("clubId"))
            e.setClubId(Long.valueOf(updates.get("clubId").toString()));

        if (updates.containsKey("status"))
            e.setStatus(ClubStatus.valueOf(updates.get("status").toString()));

        if (updates.containsKey("message"))
            e.setMessage(updates.get("message").toString());

        return toDto(repository.save(e));
    }

    // -------------------- 클럽별 신청자 조회 --------------------
    public List<ApplicationDto> findByClubId(Long clubId) {
        return repository.findByClubId(clubId).stream().map(this::toDto).toList();
    }

    // -------------------- 합격 / 불합격 처리 --------------------
    public ApplicationDto setResult(Long id, String result, String message) {

        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if ("pass".equalsIgnoreCase(result)) {
            e.setStatus(ClubStatus.PASSED);
        } else if ("fail".equalsIgnoreCase(result)) {
            e.setStatus(ClubStatus.FAILED);
        } else {
            throw new IllegalArgumentException("result 값은 pass 또는 fail 이어야 합니다.");
        }

        e.setMessage(message);
        return toDto(repository.save(e));
    }

    // -------------------- 지원자 최종 확정/철회 --------------------
    public ApplicationDto confirmOrDecline(Long id, String action) {

        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        // 현재 상태가 PASSED 또는 CONFIRMED일 때만 가능
        if (!(e.getStatus() == ClubStatus.PASSED || e.getStatus() == ClubStatus.CONFIRMED)) {
            throw new IllegalStateException("현재 상태에서 확정/철회 불가: " + e.getStatus());
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

    // -------------------- 추가 합격 --------------------
    public ApplicationDto additionalOffer(Long id) {

        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if (e.getStatus() != ClubStatus.FAILED) {
            throw new IllegalStateException("FAILED 상태만 추가 합격 가능");
        }

        e.setStatus(ClubStatus.PASSED);
        e.setMessage("추가 합격 통보");

        return toDto(repository.save(e));
    }

    // -------------------- 학과별 클럽 요약 조회 --------------------
    public List<ClubSummary> getClubsByDepartment(Long departmentId) {
        return repository.findClubsByDepartment(departmentId);
    }

    // =====================================================
    //                 내부 변환 메서드
    // =====================================================

    private ApplicationDto toDto(Application e) {
        return new ApplicationDto(
                e.getId(),
                e.getUserId(),
                e.getClubId(),
                e.getStatus(),
                e.getMessage(),
                e.getCreatedAt()
        );
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
