// src/main/java/com/JoinUs/dp/service/ApplicationService.java
package com.JoinUs.dp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.JoinUs.dp.common.exception.NotFoundException;
import com.JoinUs.dp.dto.ApplicationDto;
import com.JoinUs.dp.dto.ClubSummary;
import com.JoinUs.dp.entity.Application;
import com.JoinUs.dp.entity.ClubStatus;
import com.JoinUs.dp.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository repository;

    /** 신청 등록 */
    public ApplicationDto apply(ApplicationDto dto) {
        Application e = toEntity(dto);
        e.setId(null);
        e.setStatus(ClubStatus.PENDING);
        e.setCreatedAt(LocalDateTime.now());
        return toDto(repository.save(e));
    }

    /** 전체 조회 */
    public List<ApplicationDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    /** 단건 조회 */
    public ApplicationDto findById(Long id) {
        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청 없음 id=" + id));
        return toDto(e);
    }

    /** 삭제 */
    public void cancel(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("신청 없음 id=" + id);
        }
        repository.deleteById(id);
    }

    /** 전체 수정 */
    public ApplicationDto update(ApplicationDto dto) {
        if (dto.getApplicationId() == null) {
            throw new IllegalArgumentException("id 필요");
        }

        Application e = repository.findById(dto.getApplicationId())
                .orElseThrow(() -> new NotFoundException("신청 없음 id=" + dto.getApplicationId()));

        e.setUserId(dto.getUserId());
        e.setClubId(dto.getClubId());
        e.setStatus(dto.getStatus());
        e.setMessage(dto.getMessage());

        return toDto(repository.save(e));
    }

    /** 부분 수정 */
    public ApplicationDto partialUpdate(Long id, Map<String, Object> updates) {
        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청 없음 id=" + id));

        if (updates.containsKey("userId"))
            e.setUserId(Long.valueOf(updates.get("userId").toString()));

        if (updates.containsKey("clubId"))
            e.setClubId(Long.valueOf(updates.get("clubId").toString()));

        if (updates.containsKey("status"))
            e.setStatus(ClubStatus.valueOf(updates.get("status").toString()));

        if (updates.containsKey("message"))
            e.setMessage((String) updates.get("message"));

        return toDto(repository.save(e));
    }

    /** 클럽별 조회 */
    public List<ApplicationDto> findByClubId(Long clubId) {
        return repository.findByClubId(clubId).stream().map(this::toDto).toList();
    }

    /** 합격/불합격 설정 */
    public ApplicationDto setResult(Long id, String result, String message) {
        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청 없음 id=" + id));

        if ("pass".equalsIgnoreCase(result)) {
            e.setStatus(ClubStatus.PASSED);
        } else if ("fail".equalsIgnoreCase(result)) {
            e.setStatus(ClubStatus.FAILED);
        } else {
            throw new IllegalArgumentException("result는 pass 또는 fail만 가능");
        }

        e.setMessage(message);
        return toDto(repository.save(e));
    }

    /** 합격자 확정/철회 */
    public ApplicationDto confirmOrDecline(Long id, String action) {
        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청 없음 id=" + id));

        if (!(e.getStatus() == ClubStatus.PASSED || e.getStatus() == ClubStatus.CONFIRMED)) {
            throw new IllegalStateException("현재 상태에서 확정/철회 불가");
        }

        if ("confirm".equalsIgnoreCase(action)) {
            e.setStatus(ClubStatus.CONFIRMED);
        } else if ("decline".equalsIgnoreCase(action)) {
            e.setStatus(ClubStatus.DECLINED);
        } else {
            throw new IllegalArgumentException("action은 confirm 또는 decline");
        }

        return toDto(repository.save(e));
    }

    /** 추가 합격 통보 */
    public ApplicationDto additionalOffer(Long id) {
        Application e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청 없음 id=" + id));

        if (e.getStatus() != ClubStatus.FAILED) {
            throw new IllegalStateException("FAILED 상태만 추가 합격 가능");
        }

        e.setStatus(ClubStatus.PASSED);
        e.setMessage("추가 합격 통보");

        return toDto(repository.save(e));
    }

    /** 학과별 클럽 목록 - (희동/국진 요구사항 포함 시 구현) */
    public List<ClubSummary> getClubsByDepartment(Long departmentId) {
        return List.of(); // TODO 희동/국진 구조 완성되면 연결
    }

    /** 내부 변환 */
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
