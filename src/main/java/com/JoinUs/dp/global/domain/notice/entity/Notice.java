package com.JoinUs.dp.global.domain.notice.entity;

import java.time.LocalDateTime;

public class Notice {
    public enum Type {
        FAQ, CLUB_NOTICE
    }

    private Long id;
    private Long clubId;              // 클럽 공지일 때만 세팅 (FAQ는 null)
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Type type;

    public Notice(Long id, Long clubId, String title, String content, LocalDateTime createdAt, Type type) {
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
    public Type getType() { return type; }

    public void setId(Long id) { this.id = id; }
    public void setClubId(Long clubId) { this.clubId = clubId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setType(Type type) { this.type = type; }
}