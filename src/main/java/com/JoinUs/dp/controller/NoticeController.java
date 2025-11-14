// src/main/java/com/JoinUs/dp/global/domain/notice/controller/NoticeController.java
package com.JoinUs.dp.controller;

import com.JoinUs.dp.common.api.ApiPath;
import com.JoinUs.dp.common.response.Response;
import com.JoinUs.dp.dto.NoticeCreateRequest;
import com.JoinUs.dp.dto.NoticeResponse;
import com.JoinUs.dp.service.NoticeService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPath.NOTICE_PATH)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService; // ✅ new 금지, DI 받기


    @GetMapping
    public ResponseEntity<Response<List<NoticeResponse>>> getFaqs() {
        List<NoticeResponse> list = noticeService.getFaqList();
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), list, "FAQ 목록 조회 성공"));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<Response<NoticeResponse>> getDetail(@PathVariable("noticeId") Long noticeId) {
        NoticeResponse dto = noticeService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), dto, "공지 상세 조회 성공"));
    }
    // ✅ FAQ 등록 (중복 경로 제거)
    @PostMapping
    public ResponseEntity<Response<NoticeResponse>> createFaq(@RequestBody NoticeCreateRequest req) {
        NoticeResponse saved = noticeService.createFaq(req.getTitle(), req.getContent());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), saved, "FAQ 등록 성공"));
    }

}
