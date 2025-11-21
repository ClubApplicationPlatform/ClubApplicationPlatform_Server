package com.JoinUs.dp.service;

import com.JoinUs.dp.dto.*;
import com.JoinUs.dp.dto.ClubCreateRequest;
import com.JoinUs.dp.entity.*;
import com.JoinUs.dp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubImageRepository imageRepository;

    // 1. 동아리 생성
    public Long createClub(ClubCreateRequest req) {
        Club club = new Club();
        club.setName(req.getName());
        club.setShortDesc(req.getShortDesc());
        club.setDescription(req.getDescription());
        club.setType(req.getType());
        club.setDepartment(req.getDepartment());
        club.setCategory(req.getCategory());
        club.setLeaderId(req.getLeaderId());
        club.setRecruitStatus("closed");  // 초기값
        clubRepository.save(club);
        return club.getClubId();
    }

    // 2. 동아리 상세 조회
    public ClubDetailResponse getClubDetail(Long id) {

        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        var images = imageRepository.findByClub_ClubId(id)
                .stream().map(ClubImage::getImageUrl)
                .collect(Collectors.toList());

        return new ClubDetailResponse(
                club.getClubId(),
                club.getName(),
                club.getShortDesc(),
                club.getDescription(),
                club.getType(),
                club.getDepartment(),
                club.getCategory(),
                club.getRecruitStatus(),
                images
        );
    }

    // 3. 전체 목록 조회
    public List<ClubListResponse> findAllClubs() {
        return clubRepository.findAll().stream()
                .map(c -> new ClubListResponse(
                        c.getClubId(),
                        c.getName(),
                        c.getShortDesc(),
                        c.getType(),
                        c.getCategory(),
                        c.getDepartment(),
                        c.getRecruitStatus()
                )).collect(Collectors.toList());
    }

    // 4. 일반/전공/카테고리/전공학과 조회
    public List<ClubListResponse> findByType(String type) {
        return clubRepository.findByType(type).stream()
                .map(c -> new ClubListResponse(
                        c.getClubId(), c.getName(), c.getShortDesc(),
                        c.getType(), c.getCategory(), c.getDepartment(),
                        c.getRecruitStatus()
                )).collect(Collectors.toList());
    }

    public List<ClubListResponse> findByTypeAndCategory(String type, String category) {
        return clubRepository.findByTypeAndCategory(type, category).stream()
                .map(c -> new ClubListResponse(
                        c.getClubId(), c.getName(), c.getShortDesc(),
                        c.getType(), c.getCategory(), c.getDepartment(),
                        c.getRecruitStatus()
                )).collect(Collectors.toList());
    }

    public List<ClubListResponse> findByDepartment(String department) {
        return clubRepository.findByDepartment(department).stream()
                .map(c -> new ClubListResponse(
                        c.getClubId(), c.getName(), c.getShortDesc(),
                        c.getType(), c.getCategory(), c.getDepartment(),
                        c.getRecruitStatus()
                )).collect(Collectors.toList());
    }

    // 5. 이미지 업로드
    public Long uploadClubImage(Long clubId, MultipartFile file) {

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        String url = "/images/" + file.getOriginalFilename(); // 실제 저장 로직 필요

        ClubImage image = new ClubImage();
        image.setClub(club);
        image.setImageUrl(url);
        imageRepository.save(image);

        return image.getImageId();
    }

    // 6. 모집 상태 변경
    public String updateRecruitStatus(Long id, String status) {

        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        // 모집 시작하면 start_date 자동 입력
        if (status.equals("open")) {
            club.setRecruitmentStartDate(Date.valueOf(LocalDate.now()));
        }

        club.setRecruitStatus(status);
        clubRepository.save(club);

        return "updated";
    }

    // 7. 모집 마감일 설정
    public String updateDeadline(Long id, String endDate) {

        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        club.setRecruitmentEndDate(Date.valueOf(endDate));
        clubRepository.save(club);
        return "deadline updated";
    }

    // 8. 모집 종료
    public String closeRecruit(Long id) {

        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));

        club.setRecruitStatus("closed");
        clubRepository.save(club);

        return "closed";
    }
}
