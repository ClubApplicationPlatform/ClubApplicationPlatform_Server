// src/main/java/com/JoinUs/dp/global/domain/application/repository/ClubApplicationRepository.java
package com.JoinUs.dp.repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.JoinUs.dp.dto.ClubSummary;
import com.JoinUs.dp.entity.ClubApplication;
import com.JoinUs.dp.entity.ClubStatus;
import com.JoinUs.dp.entity.QuestionAnswer;

@Repository
public class ClubApplicationRepository {

    private final Map<Long, ClubApplication> storage = Collections.synchronizedMap(new LinkedHashMap<>());
    private final AtomicLong seq = new AtomicLong(1);

    public ClubApplicationRepository() {
        // 샘플 데이터
        ClubApplication a = new ClubApplication();
        a.setId(seq.getAndIncrement());
        a.setClubId(101L);
        a.setDepartmentId(11L);
        a.setClubName("개발동아리");
        a.setApplicantName("홍길동");
        a.setAppliedDate(LocalDate.now().minusDays(2));
        a.setQuestionAnswers(List.of(new QuestionAnswer("지원 동기", "경험을 쌓고 싶어서")));
        a.setStatus(ClubStatus.PENDING);
        storage.put(a.getId(), a);

        ClubApplication b = new ClubApplication();
        b.setId(seq.getAndIncrement());
        b.setClubId(202L);
        b.setDepartmentId(22L);
        b.setClubName("사진동아리");
        b.setApplicantName("이몽룡");
        b.setAppliedDate(LocalDate.now().minusDays(1));
        b.setQuestionAnswers(List.of(new QuestionAnswer("특기", "사진 보정")));
        b.setStatus(ClubStatus.PENDING);
        storage.put(b.getId(), b);
    }

    public ClubApplication save(ClubApplication app) {
        if (app.getId() == null) {
            app.setId(seq.getAndIncrement());
        }
        if (app.getStatus() == null) {
            app.setStatus(ClubStatus.PENDING);
        }
        if (app.getAppliedDate() == null) {
            app.setAppliedDate(LocalDate.now());
        }
        storage.put(app.getId(), app);
        return app;
    }

    public Optional<ClubApplication> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<ClubApplication> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public List<ClubApplication> findByClubId(Long clubId) {
        return storage.values().stream()
                .filter(a -> Objects.equals(a.getClubId(), clubId))
                .collect(Collectors.toList());
    }

    public List<ClubSummary> findClubsByDepartmentId(Long departmentId) {
        // 신청 데이터 기반으로 학과 내 "존재하는" 클럽 목록 요약
        return storage.values().stream()
                .filter(a -> Objects.equals(a.getDepartmentId(), departmentId))
                .collect(Collectors.toMap(
                        ClubApplication::getClubId,
                        ClubApplication::getClubName,
                        (v1, v2) -> v1
                ))
                .entrySet().stream()
                .map(e -> new ClubSummary(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }
}
