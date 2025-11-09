package com.JoinUs.dp.domain.interview;

import com.JoinUs.dp.domain.club.entity.Club;
import com.JoinUs.dp.domain.club.repository.ClubRepository;
import com.JoinUs.dp.domain.interview.entity.Applicant;
import com.JoinUs.dp.domain.interview.entity.Interview;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewConfirmedEnum;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewResultEnum;
import com.JoinUs.dp.domain.interview.repository.ApplicantRepository;
import com.JoinUs.dp.domain.interview.repository.InterviewRepository;
import com.JoinUs.dp.domain.interview.service.InterviewService;
import com.JoinUs.dp.domain.user.dto.request.RegisterDto;
import com.JoinUs.dp.domain.user.entity.User;
import com.JoinUs.dp.domain.user.repository.UserRepository;
import com.JoinUs.dp.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


// 테스트 데이터 생성 클래스 파일 (삭제해도 무방함)
@Component("interviewDataSeeder")
@RequiredArgsConstructor
public class SetData implements CommandLineRunner {
    private final InterviewService interviewService;
    private final AuthService authService;
    private final InterviewRepository interviewRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;

    private void UserDataSeeding(){
        authService.register(new RegisterDto(
                "user1@gmail.com",
                "user1",
                "user1"
        ));
        authService.register(new RegisterDto(
                "user2@gmail.com",
                "user2",
                "user2"
        ));
        authService.register(new RegisterDto(
                "user3@gmail.com",
                "user3",
                "user3"
        ));
        authService.register(new RegisterDto(
                "user4@gmail.com",
                "user4",
                "user4"
        ));
        authService.register(new RegisterDto(
                "user5@gmail.com",
                "user5",
                "user5"
        ));
        authService.register(new RegisterDto(
                "user6@gmail.com",
                "user6",
                "user6"
        ));
        authService.register(new RegisterDto(
                "user7@gmail.com",
                "user7",
                "user7"
        ));

    }

    private void ClubDataSeeding(){
        List<Club> clubs = List.of(
                new Club("밴드부"),
                new Club("OTT")
        );
        clubRepository.saveAll(clubs);
    }

//    private void InterviewDataSeeding(){
//        Club club = clubRepository.findById(1L).orElse(null);
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//
//        List<Interview> interviews = List.of(
//                new Interview(
//                        club,
//                        LocalDateTime.parse("2025-10-11T13:00", fmt),
//                        LocalDateTime.parse("2025-10-11T13:30", fmt),
//                        "산학협동관 I208호",
//                        3L
//                ),
//                new Interview(
//                        club,
//                        LocalDateTime.parse("2025-10-11T13:00", fmt),
//                        LocalDateTime.parse("2025-10-11T13:30", fmt),
//                        "산학협동관 I210호",
//                        3L
//                ),
//                new Interview(
//                        club,
//                        LocalDateTime.parse("2025-10-11T13:30", fmt),
//                        LocalDateTime.parse("2025-10-11T14:00", fmt),
//                        "산학협동관 I208호",
//                        3L
//                )
//        );
//        interviewRepository.saveAll(interviews);
//    }

//
//    private void ApplyInterview(Long interviewId, List<String> userNameList) {
//        for (String userName : userNameList) {
//            User user = userRepository.findByUsername(userName);
//            String userEmail = user.getEmail();
//            interviewService.apply(interviewId, userEmail);
//        }
//    }
//
//    private void SetInterviewResult(List<String> userNameList, InterviewResultEnum status) {
//        for (String userName : userNameList) {
//            Applicant applicant = applicantRepository.findByUser_Username(userName);
//            applicant.setInterviewResult(status);
//            applicantRepository.save(applicant);
//        }
//    }
//
//    private void SetUserConfirmStatus(List<String> userNameList, InterviewConfirmedEnum status) {
//        for (String userName : userNameList) {
//            Applicant applicant = applicantRepository.findByUser_Username(userName);
//            applicant.setConfirmStatus(status);
//            applicantRepository.save(applicant);
//        }
//}

    public void run(String[] args){
        UserDataSeeding();
        ClubDataSeeding();
//        InterviewDataSeeding();

//        ApplyInterview(1L, List.of("user1",  "user2", "user3"));
//        ApplyInterview(2L, List.of("user4", "user5"));
//        ApplyInterview(3L, List.of("user6", "user7"));
//
//        SetInterviewResult(List.of("user1", "user2", "user3", "user4"), InterviewResultEnum.PASSED);
//        SetInterviewResult(List.of("user5", "user6", "user7"), InterviewResultEnum.FAILED);
//        SetUserConfirmStatus(List.of("user1", "user2"), InterviewConfirmedEnum.CONFIRMED);
//        SetUserConfirmStatus(List.of("user3"), InterviewConfirmedEnum.DECLINED);
    }
}
