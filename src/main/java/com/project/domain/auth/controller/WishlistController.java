package com.project.domain.auth.controller;

import com.project.domain.auth.entity.LikedClub;
import com.project.domain.auth.repository.LikedClubRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final LikedClubRepository likedClubRepository;

    public WishlistController(LikedClubRepository likedClubRepository) {
        this.likedClubRepository = likedClubRepository;
    }

    // 찜 추가
    @PostMapping
    public ResponseEntity<String> likeClub(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String type = payload.get("type");
        String category = payload.get("category");
        String department = payload.get("department");

        LikedClub likedClub = new LikedClub(name, type, category, department);
        likedClubRepository.save(likedClub);
        return ResponseEntity.ok(name + " 찜 완료!");
    }

    // 찜 삭제
    @DeleteMapping("/{clubId}")
    public ResponseEntity<String> unlikeClub(@PathVariable Long clubId) {
        if (!likedClubRepository.existsById(clubId)) {
            return ResponseEntity.notFound().build();
        }
        likedClubRepository.deleteById(clubId);
        return ResponseEntity.ok("찜 삭제 완료!");
    }

    // 전체 목록 or type 필터
    @GetMapping
    public List<LikedClub> getWishlist(@RequestParam(required = false) String type) {
        if (type != null) {
            return likedClubRepository.findByType(type);
        }
        return likedClubRepository.findAll();
    }

    // 일반 카테고리별
    @GetMapping("/general/{category}")
    public List<LikedClub> getGeneralByCategory(@PathVariable String category) {
        return likedClubRepository.findByTypeAndCategory("general", category);
    }

    // 전공 학과별
    @GetMapping("/major/{department}")
    public List<LikedClub> getMajorByDepartment(@PathVariable String department) {
        return likedClubRepository.findByTypeAndDepartment("major", department);
    }
}
