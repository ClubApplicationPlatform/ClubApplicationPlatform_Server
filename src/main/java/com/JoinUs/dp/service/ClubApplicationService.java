// src/main/java/com/JoinUs/dp/global/domain/application/service/ClubApplicationService.java
package com.JoinUs.dp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional; // ← 트랜잭션이 필요하면 주석 해제

import com.JoinUs.dp.common.exception.NotFoundException;
import com.JoinUs.dp.dto.ClubSummary;
import com.JoinUs.dp.entity.ClubApplication;
import com.JoinUs.dp.dto.ClubApplicationDto;
import com.JoinUs.dp.entity.ClubStatus;
import com.JoinUs.dp.repository.ClubApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubApplicationService {

    // ✅ 잘못된 자기 주입 제거, Repository 주입으로 수정
    private final ClubApplicationRepository repository;
   
    // -------------------- 기본 CRUD --------------------

    // @Transactional
    public ClubApplicationDto apply(ClubApplicationDto dto) {
        ClubApplication e = toEntity(dto);
        e.setId(null);
        e.setStatus(ClubStatus.PENDING);
        e.setAppliedDate(e.getAppliedDate() == null ? LocalDate.now() : e.getAppliedDate());
        return toDto(repository.save(e));
    }

    public List<ClubApplicationDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public ClubApplicationDto findById(Long id) {
        ClubApplication e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));
        return toDto(e);
    }

    // @Transactional
    public void cancel(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("신청을 찾을 수 없습니다. id=" + id);
        }
        repository.deleteById(id);
    }

    // @Transactional
    public ClubApplicationDto update(ClubApplicationDto dto) {
        if (dto.getId() == null) throw new IllegalArgumentException("id는 필수입니다.");
        ClubApplication e = repository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + dto.getId()));

        e.setClubId(dto.getClubId());
        e.setDepartmentId(dto.getDepartmentId());
        e.setApplicantName(dto.getApplicantName());
        e.setClubName(dto.getClubName());
        e.setAppliedDate(dto.getAppliedDate());
        e.setQuestionAnswers(dto.getQuestionAnswers());
        e.setStatus(dto.getStatus());
        e.setResultMessage(dto.getResultMessage());

        return toDto(repository.save(e));
    }

    // @Transactional
    public ClubApplicationDto partialUpdate(Long id, Map<String, Object> updates) {
        ClubApplication e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if (updates.containsKey("clubId")) e.setClubId(Long.valueOf(updates.get("clubId").toString()));
        if (updates.containsKey("departmentId")) e.setDepartmentId(Long.valueOf(updates.get("departmentId").toString()));
        if (updates.containsKey("applicantName")) e.setApplicantName((String) updates.get("applicantName"));
        if (updates.containsKey("clubName")) e.setClubName((String) updates.get("clubName"));
        if (updates.containsKey("appliedDate")) e.setAppliedDate(LocalDate.parse((String) updates.get("appliedDate")));
        if (updates.containsKey("resultMessage")) e.setResultMessage((String) updates.get("resultMessage"));
        // NOTE: questionAnswers / status는 별도 DTO/검증을 권장

        return toDto(repository.save(e));
    }

    // -------------------- 추가 기능 --------------------

    public List<ClubApplicationDto> findByClubId(Long clubId) {
        return repository.findByClubId(clubId).stream().map(this::toDto).toList();
    }

    // @Transactional
    public ClubApplicationDto setResult(Long id, String result, String message) {
        ClubApplication e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        if ("pass".equalsIgnoreCase(result)) {
            e.setStatus(ClubStatus.PASSED);
        } else if ("fail".equalsIgnoreCase(result)) {
            e.setStatus(ClubStatus.FAILED);
        } else {
            throw new IllegalArgumentException("result 값은 pass 또는 fail 이어야 합니다.");
        }
        e.setResultMessage(message);
        return toDto(repository.save(e));
    }

    // @Transactional
    public ClubApplicationDto confirmOrDecline(Long id, String action) {
        ClubApplication e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));

        // 기본 정책: PASSED 상태에서 확정/철회 가능, (옵션) 확정 후 철회도 허용
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

    // @Transactional
    public ClubApplicationDto additionalOffer(Long id) {
        ClubApplication e = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("신청을 찾을 수 없습니다. id=" + id));
        if (e.getStatus() != ClubStatus.FAILED) {
            throw new IllegalStateException("FAILED 상태인 지원자에게만 추가 합격 통보가 가능합니다.");
        }
        e.setStatus(ClubStatus.PASSED);
        e.setResultMessage("추가 합격 통보");
        return toDto(repository.save(e));
    }

    public List<ClubSummary> getClubsByDepartment(Long departmentId) {
        return repository.findClubsByDepartmentId(departmentId);
    }

    // -------------------- 내부 변환 --------------------

    private ClubApplicationDto toDto(ClubApplication e) {
        return ClubApplicationDto.from(e);
    }

    private ClubApplication toEntity(ClubApplicationDto dto) {
        ClubApplication e = new ClubApplication();
        e.setId(dto.getId());
        e.setClubId(dto.getClubId());
        e.setDepartmentId(dto.getDepartmentId());
        e.setApplicantName(dto.getApplicantName());
        e.setClubName(dto.getClubName());
        e.setAppliedDate(dto.getAppliedDate());
        e.setQuestionAnswers(dto.getQuestionAnswers());
        e.setStatus(dto.getStatus());
        e.setResultMessage(dto.getResultMessage());
        return e;
    }
}
