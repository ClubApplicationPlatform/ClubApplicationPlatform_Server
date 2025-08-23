package com.JoinUs.dp.global.domain.notice.dto;

import java.time.LocalDateTime;

import com.JoinUs.dp.global.domain.notice.entity.Notice;

public class NoticeResponse {
    private Long id;
    private Long clubId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Notice.Type type;

    public NoticeResponse(Long id, Long clubId, String title, String content, LocalDateTime createdAt, Notice.Type type) {
        this.id = id;
        this.clubId = clubId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.type = type;
    }

    public Long getId() { return id; }
    public Long getClubId() { return clubId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Notice.Type getType() { return type; }

    public static NoticeResponse from(Notice n) {
        return new NoticeResponse(n.getId(), n.getClubId(), n.getTitle(), n.getContent(), n.getCreatedAt(), n.getType());
    }
}