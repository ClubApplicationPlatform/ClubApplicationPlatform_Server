package com.JoinUs.dp.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.JoinUs.dp.entity.Notice;
import com.JoinUs.dp.entity.Notice.Type;

@Repository
public class NoticeRepository {

    private final List<Notice> store = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong seq = new AtomicLong(1);

    public NoticeRepository() {
        // 샘플 데이터 (FAQ 2개, 클럽공지 2개)
        store.add(new Notice(seq.getAndIncrement(), null, "가입/로그인 안내", "회원가입 및 로그인 방법을 안내합니다.", LocalDateTime.now().minusDays(5), Type.FAQ));
        store.add(new Notice(seq.getAndIncrement(), null, "문의 처리시간", "문의는 평일 10:00~18:00 처리됩니다.", LocalDateTime.now().minusDays(3), Type.FAQ));
        store.add(new Notice(seq.getAndIncrement(), 101L, "개발동아리 세미나 공지", "8/25(월) 18:00, 3층 세미나실", LocalDateTime.now().minusDays(2), Type.CLUB_NOTICE));
        store.add(new Notice(seq.getAndIncrement(), 202L, "사진동아리 출사 공지", "8/28(목) 10:00, 정문 집결", LocalDateTime.now().minusDays(1), Type.CLUB_NOTICE));
    }

    /** FAQ 전체 조회 */
    public List<Notice> findAllFaq() {
        return store.stream()
                .filter(n -> n.getType() == Type.FAQ)
                .sorted(Comparator.comparing(Notice::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /** 단건 조회 */
    public Optional<Notice> findById(Long id) {
        return store.stream().filter(n -> Objects.equals(n.getId(), id)).findFirst();
    }

    /** 특정 클럽 공지 목록 */
    public List<Notice> findByClubId(Long clubId) {
        return store.stream()
                .filter(n -> n.getType() == Type.CLUB_NOTICE && Objects.equals(n.getClubId(), clubId))
                .sorted(Comparator.comparing(Notice::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    /** 클럽 공지 등록 */
    public Notice saveClubNotice(Long clubId, String title, String content) {
        Notice n = new Notice(
                seq.getAndIncrement(),
                clubId,
                title,
                content,
                LocalDateTime.now(),
                Type.CLUB_NOTICE
        );
        store.add(n);
        return n;
    }

    /** ✅ FAQ 등록 (요청하신 메서드 추가) */
    public Notice saveFaq(String title, String content) {
        Notice n = new Notice(
                seq.getAndIncrement(),
                null,               // clubId 없음
                title,
                content,
                LocalDateTime.now(),
                Type.FAQ
        );
        store.add(n);
        return n;
    }
}
