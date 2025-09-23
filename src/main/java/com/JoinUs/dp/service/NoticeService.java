package com.JoinUs.dp.service;

import com.JoinUs.dp.common.exception.NotFoundException;
import com.JoinUs.dp.dto.NoticeResponse;
import com.JoinUs.dp.entity.Notice;
import com.JoinUs.dp.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    // ✅ 명시적 생성자 주입
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }
    // FAQ 목록
    public List<NoticeResponse> getFaqList() {
        return noticeRepository.findAllFaq().stream()
                .map(NoticeResponse::from)
                .toList();
    }

    // 공지 상세 (FAQ/클럽공지 공통)
    public NoticeResponse getNoticeDetail(Long id) {
        Notice n = noticeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 공지를 찾을 수 없습니다. id=" + id));
        return NoticeResponse.from(n);
    }

    // 특정 클럽 공지 목록
    public List<NoticeResponse> getClubNotices(Long clubId) {
        return noticeRepository.findByClubId(clubId).stream()
                .map(NoticeResponse::from)
                .toList();
    }

    // 특정 클럽 공지 등록
    public NoticeResponse createClubNotice(Long clubId, String title, String content) {
        Notice saved = noticeRepository.saveClubNotice(clubId, title, content);
        return NoticeResponse.from(saved);
    }

    // ✅ FAQ 등록 (Repository.saveFaq 사용)
    public NoticeResponse createFaq(String title, String content) {
        Notice saved = noticeRepository.saveFaq(title, content);
        return NoticeResponse.from(saved);
    }
}
