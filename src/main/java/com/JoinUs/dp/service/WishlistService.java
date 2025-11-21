package com.JoinUs.dp.service;

import com.JoinUs.dp.dto.WishlistResponse;
import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.entity.User;
import com.JoinUs.dp.entity.Wishlist;
import com.JoinUs.dp.repository.ClubRepository;
import com.JoinUs.dp.repository.UserRepository;
import com.JoinUs.dp.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;

    /** 찜 추가 */
    public WishlistResponse addWishlist(Long userId, Long clubId) {

        if (wishlistRepository.existsByUserIdAndClubClubId(userId, clubId)) {
            throw new RuntimeException("이미 찜한 동아리입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("동아리 없음"));

        Wishlist wish = new Wishlist(user, club);
        wishlistRepository.save(wish);

        return new WishlistResponse(
                club.getClubId(),
                club.getName(),
                club.getType(),
                club.getCategory(),
                club.getDepartment()
        );
    }

    /** 찜 삭제 */
    public void deleteWishlist(Long userId, Long clubId) {
        Wishlist wish = wishlistRepository.findByUserIdAndClubClubId(userId, clubId)
                .orElseThrow(() -> new RuntimeException("찜한 동아리 없음"));

        wishlistRepository.delete(wish);
    }

    /** 전체 조회 (type 필터 optional) */
    public List<WishlistResponse> getWishlist(Long userId, String type) {

        return wishlistRepository.findByUserIdAndClubType(userId, type).stream()
                .map(w -> new WishlistResponse(
                        w.getClub().getClubId(),
                        w.getClub().getName(),
                        w.getClub().getType(),
                        w.getClub().getCategory(),
                        w.getClub().getDepartment()
                ))
                .toList();
    }

    /** 일반동아리 카테고리별 */
    public List<WishlistResponse> getGeneralByCategory(Long userId, String category) {

        return wishlistRepository.findGeneralByUserIdAndCategory(userId, category).stream()
                .map(w -> new WishlistResponse(
                        w.getClub().getClubId(),
                        w.getClub().getName(),
                        w.getClub().getType(),
                        w.getClub().getCategory(),
                        w.getClub().getDepartment()
                ))
                .toList();
    }

    /** 전공동아리 학과별 */
    public List<WishlistResponse> getMajorByDepartment(Long userId, String department) {

        return wishlistRepository.findMajorByUserIdAndDepartment(userId, department).stream()
                .map(w -> new WishlistResponse(
                        w.getClub().getClubId(),
                        w.getClub().getName(),
                        w.getClub().getType(),
                        w.getClub().getCategory(),
                        w.getClub().getDepartment()
                ))
                .toList();
    }
}
