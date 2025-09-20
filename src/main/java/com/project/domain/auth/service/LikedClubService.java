package com.project.domain.auth.service;

import com.project.domain.auth.dto.LikedClubRequest;
import com.project.domain.auth.entity.LikedClub;
import com.project.domain.auth.repository.LikedClubRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikedClubService {

    private final LikedClubRepository likedClubRepository;

    @Transactional
    public LikedClub likeClub(LikedClubRequest request) {
        LikedClub club = new LikedClub(
                request.getName(),
                request.getType(),
                request.getCategory(),
                request.getDepartment()
        );
        return likedClubRepository.save(club);
    }

    @Transactional
    public void unlikeClub(Long clubId) {
        if (!likedClubRepository.existsById(clubId)) {
            throw new EntityNotFoundException("Club not found with id " + clubId);
        }
        likedClubRepository.deleteById(clubId);
    }

    public List<LikedClub> getAllClubs(String type) {
        return type != null
                ? likedClubRepository.findByType(type)
                : likedClubRepository.findAll();
    }

    public List<LikedClub> getGeneralByCategory(String category) {
        return likedClubRepository.findByTypeAndCategory("general", category);
    }

    public List<LikedClub> getMajorByDepartment(String department) {
        return likedClubRepository.findByTypeAndDepartment("major", department);
    }

    @Transactional
    public boolean enableNotification(Long clubId) {
        return likedClubRepository.findById(clubId).map(club -> {
            club.setNotificationEnabled(true);
            return true;
        }).orElse(false);
    }
}
