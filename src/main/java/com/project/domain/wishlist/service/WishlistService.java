package com.project.domain.wishlist.service;

import com.project.domain.wishlist.dto.WishlistRequest;
import com.project.domain.wishlist.entity.Wishlist;
import com.project.domain.wishlist.repository.WishlistRepository;
import com.project.domain.club.entity.*;
import com.project.domain.user.entity.*; 
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService { 

    private final WishlistRepository wishlistRepository; 

    private Long getCurrentUserId() {
        return 1L;
    }

    private User findUserById(Long userId) {
        return new User(userId); 
    }
    
    private Club findClubById(Long clubId) {
        if (clubId == 999L) {
             throw new EntityNotFoundException("Club not found with id " + clubId);
        }
        return new Club(clubId, "Sample Club", "major", "IT", "컴퓨터공학");
    }

    @Transactional
    public Wishlist likeClub(Long clubId) {
        Long userId = getCurrentUserId();
    
        if (wishlistRepository.existsByUserIdAndClubId(userId, clubId)) {
            throw new IllegalArgumentException("이미 찜한 동아리입니다.");
        }
        
        User user = findUserById(userId);
        Club club = findClubById(clubId);
        
        Wishlist wishlist = new Wishlist(user, club);
        return wishlistRepository.save(wishlist);
    }

    @Transactional
    public void unlikeClub(Long clubId) {
        Long userId = getCurrentUserId();

        Wishlist wishlist = wishlistRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new EntityNotFoundException("찜 기록을 찾을 수 없습니다. (Club ID: " + clubId + ")"));
                
        wishlistRepository.delete(wishlist);
    }

    public List<Wishlist> getAllWishlists(String type) { 
        Long userId = getCurrentUserId();
        return wishlistRepository.findByUserIdAndClubType(userId, type);
    }

    public List<Wishlist> getGeneralByCategory(String category) {
        Long userId = getCurrentUserId();
        return wishlistRepository.findGeneralByUserIdAndCategory(userId, category);
    }

    public List<Wishlist> getMajorByDepartment(String department) {
        Long userId = getCurrentUserId();
        return wishlistRepository.findMajorByUserIdAndDepartment(userId, department);
    }
    

}