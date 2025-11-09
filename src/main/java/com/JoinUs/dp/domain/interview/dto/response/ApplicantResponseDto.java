package com.JoinUs.dp.domain.interview.dto.response;

import com.JoinUs.dp.domain.interview.entity.Applicant;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewConfirmedEnum;
import com.JoinUs.dp.domain.interview.entity.enums.InterviewResultEnum;
import com.JoinUs.dp.domain.user.entity.User;
import com.JoinUs.dp.domain.user.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

// 면접 신청자 정보 응답용 dto
public record ApplicantResponseDto(
        Long userId,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime appliedAt,

        InterviewResultEnum interviewResult,
        InterviewConfirmedEnum decided

) {
    // ApplicantEntity -> ApplicantResponseDto
    public static ApplicantResponseDto from(Applicant applicant) {
        return new ApplicantResponseDto(
                applicant.getUserId(),
                applicant.getAppliedAt(),
                applicant.getInterviewResult(),
                applicant.getConfirmStatus()
        );
    }
    // List<ApplicantEntity> -> List<ApplicantResponseDto>
    public static List<ApplicantResponseDto> fromList(List<Applicant> applicants) {
        return applicants.stream()
                .map(i -> {
                    return from(i);
                }).toList();
    }
}
