package com.JoinUs.dp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JoinUs.dp.dto.Response;
import com.JoinUs.dp.dto.WishlistRequest;
import com.JoinUs.dp.dto.WishlistResponse;
import com.JoinUs.dp.entity.Wishlist;
import com.JoinUs.dp.global.common.api.ApiPath;
import com.JoinUs.dp.service.WishlistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiPath.WISHLISTS)
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    /** ❤️ 찜 추가 */
    @PostMapping
    public ResponseEntity<Response<WishlistResponse>> likeClub(
            @Valid @RequestBody WishlistRequest request) {

        Wishlist wishlist = wishlistService.likeClub(request.getClubId());

        WishlistResponse responseDto = new WishlistResponse(wishlist.getClub());

        return ResponseEntity.ok(
                new Response<>(HttpStatus.OK, responseDto, wishlist.getClub().getName() + " 찜 완료!")
        );
    }

    /** 💔 찜 삭제 */
    @DeleteMapping("/{clubId}")
    public ResponseEntity<Response<String>> unlikeClub(@PathVariable Long clubId) {
        wishlistService.unlikeClub(clubId);

        return ResponseEntity.ok(
                new Response<>(HttpStatus.OK, null, "찜 삭제 완료!")
        );
    }

    /** 📋 전체 찜 조회 */
    @GetMapping
    public ResponseEntity<Response<List<WishlistResponse>>> getClubs(
            @RequestParam(required = false) String type) {

        List<Wishlist> wishlists = wishlistService.getAllWishlists(type);

        List<WishlistResponse> responses = wishlists.stream()
                .map(w -> new WishlistResponse(w.getClub()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new Response<>(HttpStatus.OK, responses, "전체 또는 필터링된 찜 목록")
        );
    }

    /** 📂 일반동아리 카테고리 검색 */
    @GetMapping("/general/{category}")
    public ResponseEntity<Response<List<WishlistResponse>>> getGeneral(@PathVariable String category) {

        List<Wishlist> wishlists = wishlistService.getGeneralByCategory(category);

        List<WishlistResponse> responses = wishlists.stream()
                .map(w -> new WishlistResponse(w.getClub()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new Response<>(HttpStatus.OK, responses, "일반동아리 필터링")
        );
    }

    /** 📚 전공동아리 학과 검색 */
    @GetMapping("/major/{department}")
    public ResponseEntity<Response<List<WishlistResponse>>> getMajor(@PathVariable String department) {

        List<Wishlist> wishlists = wishlistService.getMajorByDepartment(department);

        List<WishlistResponse> responses = wishlists.stream()
                .map(w -> new WishlistResponse(w.getClub()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new Response<>(HttpStatus.OK, responses, "전공동아리 필터링")
        );
    }
}
