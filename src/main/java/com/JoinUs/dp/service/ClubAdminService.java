package com.JoinUs.dp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.entity.User;
import com.JoinUs.dp.repository.ClubSearchRepository;
import com.JoinUs.dp.repository.UserRepository;

@Service
public class ClubAdminService {

    private final ClubSearchRepository clubSearchRepository;
    private final UserRepository userRepository;

    public ClubAdminService(ClubSearchRepository clubSearchRepository, UserRepository userRepository) {
        this.clubSearchRepository = clubSearchRepository;
        this.userRepository = userRepository;
    }

    /** 전체 대시보드 데이터 (유저 수 / 동아리 수) */
    public long getUserCount() {
        return userRepository.count();
    }

    public long getClubCount() {
        return clubSearchRepository.count();
    }

    /** 동아리 전체 조회 */
    public List<ClubSearch> getAllClubs() {
        return clubSearchRepository.findAll();
    }

    /** 사용자 전체 조회 */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /** 동아리 승인 처리 */
    public boolean approveClub(Long clubId) {
        return clubSearchRepository.findById(clubId)
                .map(club -> {
                    club.setRecruiting(true);  // 승인 처리 = recruiting true
                    clubSearchRepository.save(club);
                    return true;
                })
                .orElse(false);
    }
}
