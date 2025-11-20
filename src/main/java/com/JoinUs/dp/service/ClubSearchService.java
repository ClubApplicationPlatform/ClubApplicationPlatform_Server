package com.JoinUs.dp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.JoinUs.dp.entity.ClubSearch;
import com.JoinUs.dp.repository.ClubSearchRepository;
@Service
public class ClubSearchService {

    private final ClubSearchRepository clubSearchRepository;

    public ClubSearchService(ClubSearchRepository clubSearchRepository) {
        this.clubSearchRepository = clubSearchRepository;
    }

    public List<ClubSearch> searchClubs(String name, Integer memberCount, Boolean recruiting) {
        if (name == null || name.isBlank()) name = "";

        // ✅ Swagger 파라미터가 문자열인 경우 (보조 변환)
        // (선택사항: Swagger UI에서 Boolean 값이 text로 전달되는 경우 대비)
        // 이 코드는 컨트롤러에서 처리해도 OK
        // if ("true".equalsIgnoreCase(name)) recruiting = true;

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
