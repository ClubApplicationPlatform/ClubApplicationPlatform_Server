package com.JoinUs.dp.service;

import com.JoinUs.dp.dto.AdminDashboardDto;
import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.entity.User;
import com.JoinUs.dp.repository.ClubRepository;
import com.JoinUs.dp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubAdminService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    public AdminService(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    public AdminDashboardDto getDashboard() {
        long userCount = userRepository.count();
        long clubCount = clubRepository.count();
        return new AdminDashboardDto(userCount, clubCount);
    }

    public List<ClubSearch> getAllClubs() {
        return clubRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean approveClub(Long clubId) {
        return clubRepository.findById(clubId).map(club -> {
            club.setApproved(true); // Club 엔티티에 approved 필드 필요
            clubRepository.save(club);
            return true;
        }).orElse(false);
    }
}
