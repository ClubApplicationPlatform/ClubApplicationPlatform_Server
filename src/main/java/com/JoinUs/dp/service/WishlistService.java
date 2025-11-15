package com.JoinUs.dp.service;

import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.entity.User;
import com.JoinUs.dp.entity.Wishlist;
import com.JoinUs.dp.repository.ClubRepository;
import com.JoinUs.dp.repository.UserRepository;
import com.JoinUs.dp.repository.WishlistRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    /** 현재 로그인 유저 ID (임시) */
    private Long getCurrentUserId() {
        return 1L; // 로그인 구현 전 임시
    }

    /** ❤️ 찜 추가 */
    @Transactional
    public Wishlist likeClub(Long clubId) {
        Long userId = getCurrentUserId();

        // 이미 찜했는지 체크
        if (wishlistRepository.existsByUserIdAndClubId(userId, clubId)) {
            throw new IllegalArgumentException("이미 찜한 동아리입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new EntityNotFoundException("동아리를 찾을 수 없습니다."));

        Wishlist wishlist = new Wishlist(user, club);
        return wishlistRepository.save(wishlist);
    }

    /** 💔 찜 삭제 */
    @Transactional
    public void unlikeClub(Long clubId) {
        Long userId = getCurrentUserId();

        Wishlist wishlist = wishlistRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() ->
                        new EntityNotFoundException("찜 기록을 찾을 수 없습니다. (Club ID: " + clubId + ")")
                );

        wishlistRepository.delete(wishlist);
    }

    /** 📋 전체 조회 (type optional) */
    public List<Wishlist> getAllWishlists(String type) {
        Long userId = getCurrentUserId();
        return wishlistRepository.findByUserIdAndClubType(userId, type);
    }

    /** 📁 일반 동아리 */
    public List<Wishlist> getGeneralByCategory(String category) {
        Long userId = getCurrentUserId();
        return wishlistRepository.findGeneralByUserIdAndCategory(userId, category);
    }

    /** 🎓 전공 동아리 */
    public List<Wishlist> getMajorByDepartment(String department) {
        Long userId = getCurrentUserId();
        return wishlistRepository.findMajorByUserIdAndDepartment(userId, department);
    }
}
