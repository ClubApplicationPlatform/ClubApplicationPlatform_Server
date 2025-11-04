package com.project.domain.auth.repository;

import com.project.domain.auth.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByUserIdAndClubId(Long userId, Long clubId);
    
    @Query("SELECT w FROM Wishlist w JOIN FETCH w.club WHERE w.user.id = :userId AND (:type IS NULL OR w.club.type = :type)")
    List<Wishlist> findByUserIdAndClubType(@Param("userId") Long userId, @Param("type") String type);
    
    @Query("SELECT w FROM Wishlist w JOIN FETCH w.club WHERE w.user.id = :userId AND w.club.type = 'general' AND w.club.category = :category")
    List<Wishlist> findGeneralByUserIdAndCategory(@Param("userId") Long userId, @Param("category") String category);
    
    @Query("SELECT w FROM Wishlist w JOIN FETCH w.club WHERE w.user.id = :userId AND w.club.type = 'major' AND w.club.department = :department")
    List<Wishlist> findMajorByUserIdAndDepartment(@Param("userId") Long userId, @Param("department") String department);

    boolean existsByUserIdAndClubId(Long userId, Long clubId);
}