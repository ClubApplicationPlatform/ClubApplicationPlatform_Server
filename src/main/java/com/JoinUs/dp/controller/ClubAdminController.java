package com.JoinUs.dp.controller;

import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.entity.User; // 사용자 엔티티 필요
import com.JoinUs.dp.repository.ClubSearchRepository;
import com.JoinUs.dp.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clubs/admin")
public class AdminController {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    public AdminController(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    /**
     * 📊 대시보드 조회 (전체 사용자 수, 동아리 수)
     */
    @GetMapping("/dashboard")
    public Map<String, Long> getDashboard() {
        long userCount = userRepository.count();
        long clubCount = clubRepository.count();

        Map<String, Long> dashboard = new HashMap<>();
        dashboard.put("userCount", userCount);
        dashboard.put("clubCount", clubCount);

        return dashboard;
    }

    /**
     * 📋 동아리 목록 조회
     */
    @GetMapping("/clubs")
    public List<ClubSearch> getAllClubs() {
        return clubRepository.findAll();
    }

    /**
     * 👥 사용자 목록 조회
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * ✅ 동아리 승인 (approve 상태 true로 변경)
     */
    @PatchMapping("/clubs/{clubId}/approve")
    public ClubSearch approveClub(@PathVariable Long clubId) {
        ClubSearch club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("클럽을 찾을 수 없습니다."));

        club.setRecruiting(true); // 예시: recruiting 필드를 true로 바꿔 승인 처리
        return clubRepository.save(club);
    }
}
