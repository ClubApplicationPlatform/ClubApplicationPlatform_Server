package com.JoinUs.dp.domain.interview.service;


import com.JoinUs.dp.domain.club.entity.Club;
import com.JoinUs.dp.domain.club.repository.ClubRepository;
import com.JoinUs.dp.domain.interview.dto.request.CreateInterviewRequestDto;
import com.JoinUs.dp.domain.interview.dto.response.*;
import com.JoinUs.dp.domain.interview.entity.Applicant;
import com.JoinUs.dp.domain.interview.entity.Interview;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewConfirmedEnum;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewResultEnum;
import com.JoinUs.dp.domain.interview.repository.ApplicantRepository;
import com.JoinUs.dp.domain.interview.repository.InterviewRepository;
import com.JoinUs.dp.domain.user.entity.User;
import com.JoinUs.dp.domain.user.repository.UserRepository;
import com.JoinUs.dp.global.utility.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;
    private final ApplicantRepository applicantRepository;
    private final JwtProvider jwtProvider;

    // 1) 면접 회차 생성
    @Transactional
    public InterviewResponseDto create(Long clubId, CreateInterviewRequestDto req) {
        // 동아리 존재 여부 확인
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "clubId: " + clubId + " 를 찾을 수 없음"
                        )
                );

        // 시작 시간이 종료 시간보다 늦은 시간으로 주어졌을 때 에러 발생
        // 예시) startAt: 10:00, endAt: 9:00 -> 에러
        if (req.startTime().isAfter(req.endTime())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "잘못된 시간 설정"
            );
        }

        // 데이터베이스 속 생성된 모든 면접 회차와 POST 한 면접 회차 비교
        // 시간과 장소가 겹칠때 에러 발생
        List<Interview> interviews = interviewRepository.findAll();
        for (Interview interview : interviews) {
            if (req.startTime().isBefore(interview.getEndTime()) && req.endTime().isAfter(interview.getStartTime())) {
                if (interview.getLocation().equals(req.location())) {
                    Club creatingInterviewClub = clubRepository.findById(interview.getClubId()).orElseThrow();
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "(요청: " + club.getName() + ", 비교: " + creatingInterviewClub.getName() + ") 면접 시간, 장소 겹침"
                    );
                }
            }
        }

        // 면접 회차 생성
        Interview createdInterview = new Interview(
                clubId,
                req.date(),
                req.startTime(),
                req.endTime(),
                req.duration(),
                req.maxApplicants(),
                req.location()
        );

        // 생성된 면접 회차 저장
        interviewRepository.save(createdInterview);

        // InterviewEntity (entity) -> InterviewResponseDto (dto) 변환 후 리턴
        return InterviewResponseDto.from(createdInterview);
    }


    // 2) 면접 회차 조회
    @Transactional(readOnly = true)
    public CheckInterviewResponseDto check(Long clubId) {
        // 동아리 존재 여부 확인
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "clubId: " + clubId + " 를 찾을 수 없음"
                        )
                );

        // clubId 동아리 내의 모든 면접 회차 조회
        List<Interview> interviews = interviewRepository.findAllByClubId(clubId);
        // InterviewEntity (entity) -> InterviewResponseDto (dto) 로 변환
        List<InterviewResponseDto> interviewsResponse = interviews.stream()
                .map(interviewEntity -> {
                    return InterviewResponseDto.from(interviewEntity);
                }).toList();

        // 면접 회차 조회 dto 반환 (clubId, clubName, 면접회차dto)
        return new CheckInterviewResponseDto(
                clubId,
                club.getName(),
                interviewsResponse
        );
    }
    
    // 3) 면접 신청 (이메일로 식별)
    @Transactional
    public void apply(Long interviewId, String email) {
//    public void applyWithToken(Long interviewId, String token) {
//        // 유효성 확인
//        if (!jwtProvider.validateToken(token)) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰");
//        }
//
//        // 토큰 -> 이메일
//        String email = jwtProvider.extractEmail(token);

        // 이메일로 유저 조회
         User user = userRepository.findByEmail(email)
             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "email: " + email + " 를 찾을 수 없음"));

        // 면접 회차 조회
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "interviewId: " + interviewId + " 를 찾을 수 없음"));

        // 중복 신청 체크
        if (applicantRepository.existsByUserIdAndClubId(user.getId(), interview.getClubId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "동일 club 중복 신청");
        }

        // 정원 체크
        if (interview.getApplied() >= interview.getMaxApplicants()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "신청 인원 초과");
        }

        // 카운트 증가 및 저장
        interview.increaseApplied();
        applicantRepository.save(new Applicant(
                user.getId(),
                interviewId,
                interview.getClubId()
        ));
    }

    // 4) 면접 현황 조회
    @Transactional(readOnly = true)
    public CheckDetailedInterviewResponseDto checkDetailed(Long clubId) {
        // 동아리 존재 여부 확인
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "clubId: " + clubId + " 를 찾을 수 없음"
                        )
                );

        // clubId 동아리 내의 모든 면접 회차 조회
        List<Interview> interviews = interviewRepository.findAllByClubId(clubId);

        // 각 회차별로 해당 회차별 신청자 정보까지 합친 Dto 구성
        List<DetailedInterviewResponseDto> detailedInterviewResponse = interviews.stream()
                .map(interviewEntity -> {
                    List<Applicant> applicants = applicantRepository.findAllByInterviewId(interviewEntity.getId());
                    return new DetailedInterviewResponseDto(
                            interviewEntity.getId(),
                            interviewEntity.getDate(),
                            interviewEntity.getStartTime(),
                            interviewEntity.getEndTime(),
                            interviewEntity.getLocation(),
                            interviewEntity.getMaxApplicants(),
                            interviewEntity.getApplied(),
                            ApplicantResponseDto.fromList(applicants)
                    );
                }).toList();

        // 면접 현황 dto 반환 (동아리Id, 동아리이름, 면접회차(면접회차 정보 + 면접신청 유저 정보))
        return new CheckDetailedInterviewResponseDto(
                clubId,
                club.getName(),
                detailedInterviewResponse
        );
    }


    // 5) 미확정/가입거절 인원 조회
    // 추가합격 기능을 위해 만들어짐
    @Transactional(readOnly = true)
    public UnconfirmedUserResponseDto checkUnconfirmedUser(Long clubId) {
        // 동아리 존재 여부 확인
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "clubId: " + clubId + " 를 찾을 수 없음"
                        )
                );

        // clubId 동아리에 신청한 신청자들의 수 카운팅
        Long applicantCount = applicantRepository.countByClubId(clubId);

        // confirmStatus 가 아직 기본값인 PENDING(대기) 인 유저들의 수 카운팅
        Long pendingUserCount = applicantRepository.countByClubIdAndInterviewResultAndConfirmStatus(
                clubId,
                InterviewResultEnum.FAILED,
                InterviewConfirmedEnum.PENDING
        );

        // confirmStatus 가 아직 기본값인 PENDING(대기) 상태인 유저들을 찾은 후
        // 면접신청 유저 entity 리스트 -> 면접신청 유저 dto 리스트 로 변환
        List<ApplicantResponseDto> pendingUsers = ApplicantResponseDto.fromList(
                applicantRepository.findAllByClubIdAndInterviewResultAndConfirmStatus(
                        clubId,
                        InterviewResultEnum.FAILED,
                        InterviewConfirmedEnum.PENDING
                )
        );

//
//        Long confirmedCount = applicantRepository.countByInterview_Club_IdAndInterviewResultAndConfirmStatus(
//                clubId,
//                InterviewResultEnum.PASSED,
//                InterviewConfirmedEnum.CONFIRMED
//        );
//        Long declinedCount = applicantRepository.countByInterview_Club_IdAndInterviewResultAndConfirmStatus(
//                clubId,
//                InterviewResultEnum.PASSED,
//                InterviewConfirmedEnum.DECLINED
//        );

        // 미확정 인원 조회 dto 반환 (동아리Id, 동아리이름, 미확정인원수, 확정인원수, 거절인원수, 미확정 유저 리스트, 거절 유저 리스트)
        return new UnconfirmedUserResponseDto(
                clubId,
                club.getName(),
                applicantCount,
                pendingUserCount,
                pendingUsers
        );
    }
}


