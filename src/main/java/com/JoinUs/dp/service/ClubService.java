package com.JoinUs.dp.service;

import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public List<Club> searchClubs(String name, Integer memberCount, Boolean recruiting) {

        if (name == null) name = "";

        if (memberCount != null && recruiting != null) {
            return clubRepository.findByNameContainingIgnoreCaseAndMemberCountGreaterThanEqualAndRecruiting(
                    name, memberCount, recruiting);
        } else if (memberCount != null) {
            return clubRepository.findByNameContainingIgnoreCaseAndMemberCountGreaterThanEqual(name, memberCount);
        } else if (recruiting != null) {
            return clubRepository.findByNameContainingIgnoreCaseAndRecruiting(name, recruiting);
        } else {
            return clubRepository.findByNameContainingIgnoreCase(name);
        }
    }
}
