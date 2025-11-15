package com.JoinUs.dp.controller;
import com.JoinUs.dp.dto.Response;
import com.JoinUs.dp.dto.WishlistRequest;
import com.JoinUs.dp.dto.WishlistResponse;
import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.entity.Wishlist;
import com.JoinUs.dp.service.WishlistService;
import com.project.domain.club.entity.*;
import com.project.domain.global.constant.ApiPath;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import com.JoinUs.dp.common.api.*;
@RestController
@RequestMapping(ApiPath.WISHLISTS) 
@RequiredArgsConstructor
public class WishlistController { 

    private final WishlistService wishlistService; 

    @PostMapping
    public ResponseEntity<Response<WishlistResponse>> likeClub(@Valid @RequestBody WishlistRequest request) { 
        Wishlist wishlist = wishlistService.likeClub(request.getClubId());
        Club club = wishlist.getClub(); 
        WishlistResponse responseDto = new WishlistResponse(club);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, responseDto, club.getName() + " 찜 완료!"));
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Response<String>> unlikeClub(@PathVariable Long clubId) {
        wishlistService.unlikeClub(clubId);
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, null, "찜 삭제 완료!"));
    }

    @GetMapping
    public ResponseEntity<Response<List<WishlistResponse>>> getClubs(@RequestParam(required = false) String type) {
        List<Wishlist> wishlists = wishlistService.getAllWishlists(type);
        List<WishlistResponse> responses = wishlists.stream()
                .map(wishlist -> new WishlistResponse(wishlist.getClub()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, responses, "전체 또는 필터링된 찜 목록"));
    }

    @GetMapping("/general/{category}")
    public ResponseEntity<Response<List<WishlistResponse>>> getGeneral(@PathVariable String category) {
        List<Wishlist> wishlists = wishlistService.getGeneralByCategory(category);
        List<WishlistResponse> responses = wishlists.stream()
                .map(wishlist -> new WishlistResponse(wishlist.getClub()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, responses, "일반동아리 필터링"));
    }

    @GetMapping("/major/{department}")
    public ResponseEntity<Response<List<WishlistResponse>>> getMajor(@PathVariable String department) {
        List<Wishlist> wishlists = wishlistService.getMajorByDepartment(department);
        List<WishlistResponse> responses = wishlists.stream()
                .map(wishlist -> new WishlistResponse(wishlist.getClub()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Response<>(HttpStatus.OK, responses, "전공동아리 필터링"));
    }

}