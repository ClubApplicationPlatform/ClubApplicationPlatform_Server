package com.JoinUs.dp.service;

import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.repository.ClubSearchRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClubSearchService {

    private final ClubSearchRepository clubSearchRepository;

    public ClubSearchService(ClubSearchRepository clubSearchRepository) {
        this.clubSearchRepository = clubSearchRepository;
    }

    public List<ClubSearch> searchClubs(String name, Integer memberCount, Boolean recruiting) {

        if (name == null) name = "";

        if (memberCount != null && recruiting != null) {
            return clubSearchRepository.findByNameContainingIgnoreCaseAndMemberCountGreaterThanEqualAndRecruiting(
                    name, memberCount, recruiting);
        } else if (memberCount != null) {
            return clubSearchRepository.findByNameContainingIgnoreCaseAndMemberCountGreaterThanEqual(name, memberCount);
        } else if (recruiting != null) {
            return clubSearchRepository.findByNameContainingIgnoreCaseAndRecruiting(name, recruiting);
        } else {
            return clubSearchRepository.findByNameContainingIgnoreCase(name);
        }
    }
}
