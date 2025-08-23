package com.JoinUs.dp.global.domain.notice.dto;


public class NoticeCreateRequest {
    private String title;
    private String content;

    public NoticeCreateRequest() {}

    public NoticeCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}
