package com.JoinUs.dp.domain.club.service;

import com.JoinUs.dp.domain.club.dto.request.ClubCreateRequest;
import com.JoinUs.dp.domain.club.dto.request.ClubQuestionRequest;
import com.JoinUs.dp.domain.club.dto.request.RecruitStatusUpdateRequest;
import com.JoinUs.dp.domain.club.dto.response.ClubDetailResponse;
import com.JoinUs.dp.domain.club.dto.response.ClubSimpleResponse;
import com.JoinUs.dp.domain.club.entity.Club;
import com.JoinUs.dp.domain.club.entity.ClubQuestion;
import com.JoinUs.dp.domain.club.repository.ClubQuestionRepository;
import com.JoinUs.dp.domain.club.repository.ClubRepository;
import com.JoinUs.dp.domain.user.entity.User;
import com.JoinUs.dp.global.exception.ClubNotFoundException;
import com.JoinUs.dp.global.utility.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final FileUploadUtil fileUploadUtil;
    private final ClubQuestionRepository clubQuestionRepository;

    /**
     * 동아리 생성 - 로그인한 사용자(User)를 동아리장으로 설정 + PDF 파일 업로드
     */
    @Transactional
    public Long createClub(ClubCreateRequest dto, User user) throws IOException {
        // PDF 저장 (Web에서 접근 가능한 경로: /uploads/pdfs/...)
        String pdfUrl = fileUploadUtil.savePdf(dto.getPdfFile());

        Club club = Club.builder()
                .name(dto.getName())
                .shortDesc(dto.getShortDesc())
                .description(dto.getDescription())
                .activities(dto.getActivities())
                .vision(dto.getVision())
                .pdfFilePath(pdfUrl)      // ✅ 엔티티 필드명에 맞춤
                .status("pending")
                .recruitStatus("closed")
                .leader(user)             // 로그인 사용자 지정
                .build();

        clubRepository.save(club);
        return club.getId();
    }

    /**
     * 유저 없이 동아리 생성 (테스트용)
     * - leader가 NOT NULL 제약이면 엔티티/DB 제약을 완화하거나, 더미 유저를 만들어 연결해야 함
     */
    @Transactional
    public Long createClubWithoutUser(ClubCreateRequest req, MultipartFile pdfFile) throws IOException {
        // PDF 저장
        String pdfUrl = fileUploadUtil.savePdf(pdfFile);

        Club club = Club.builder()
                .name(req.getName())
                .shortDesc(req.getShortDesc())
                .description(req.getDescription())
                .activities(req.getActivities())
                .vision(req.getVision())
                .pdfFilePath(pdfUrl)      // ✅ pdfPath → pdfFilePath 로 정정
                .status("pending")
                .recruitStatus("closed")
                // .leader(null)           // leader가 nullable이면 생략/null 허용
                .build();

        clubRepository.save(club);
        return club.getId();
    }

    /**
     * 이미지 업로드
     */
    @Transactional
    public void uploadImage(Long clubId, MultipartFile imageFile) throws IOException {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        String imagePath = fileUploadUtil.saveImage(imageFile);
        club.setImageUrl(imagePath);
        clubRepository.save(club);
    }

    /**
     * 모집 상태 변경
     */
    @Transactional
    public void updateRecruitStatus(Long clubId, RecruitStatusUpdateRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        club.setRecruitStatus(request.getStatus());

        if (request.getRecruitmentNotice() != null) {
            club.setRecruitmentNotice(request.getRecruitmentNotice());
        }

        if (request.getRecruitmentStartDate() != null) {
            try {
                club.setRecruitmentStartDate(LocalDate.parse(request.getRecruitmentStartDate()));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("recruitmentStartDate 형식이 잘못되었습니다. 예: 2025-08-01");
            }
        }

        if (request.getRecruitmentEndDate() != null) {
            try {
                club.setRecruitmentEndDate(LocalDate.parse(request.getRecruitmentEndDate()));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("recruitmentEndDate 형식이 잘못되었습니다. 예: 2025-08-10");
            }
        }

        clubRepository.save(club);
    }

    /**
     * 동아리 전체 목록 조회
     */
    @Transactional
    public List<ClubSimpleResponse> getClubList(String type) {
        List<Club> clubs = (type == null)
                ? clubRepository.findAll()
                : clubRepository.findByType(type);

        return clubs.stream()
                .map(ClubSimpleResponse::from)
                .toList();
    }

    /**
     * 동아리 상세 조회
     */
    @Transactional
    public ClubDetailResponse getClubDetail(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);
        return ClubDetailResponse.from(club);
    }

    /**
     * 질문 등록
     */
    @Transactional
    public Long registerQuestion(Long clubId, ClubQuestionRequest request) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(ClubNotFoundException::new);

        if ("open".equals(club.getRecruitStatus())) {
            throw new RuntimeException("모집 시작 이후에는 질문을 등록할 수 없습니다.");
        }

        ClubQuestion question = ClubQuestion.builder()
                .club(club)
                .question(request.getQuestion())
                .build();

        clubQuestionRepository.save(question);
        return question.getId();
    }

    // ✅ 전공 동아리 전체 조회
    @Transactional
    public List<ClubSimpleResponse> getMajorClubs() {
        return clubRepository.findByType("major").stream()
                .map(ClubSimpleResponse::from)
                .toList();
    }

    // ✅ 전공 동아리 과별 조회
    @Transactional
    public List<ClubSimpleResponse> getMajorClubsByDepartment(String department) {
        return clubRepository.findByTypeAndDepartment("major", department).stream()
                .map(ClubSimpleResponse::from)
                .toList();
    }

    // ✅ 일반 동아리 전체 조회
    @Transactional
    public List<ClubSimpleResponse> getGeneralClubs() {
        return clubRepository.findByType("general").stream()
                .map(ClubSimpleResponse::from)
                .toList();
    }

    // ✅ 일반 동아리 카테고리별 조회
    @Transactional
    public List<ClubSimpleResponse> getGeneralClubsByCategory(String category) {
        return clubRepository.findByTypeAndCategory("general", category).stream()
                .map(ClubSimpleResponse::from)
                .toList();
    }
}
