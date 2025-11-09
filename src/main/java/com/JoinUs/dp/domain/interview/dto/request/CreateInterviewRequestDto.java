package com.JoinUs.dp.domain.interview.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// 면접 회차 생성 바디 dto
public record CreateInterviewRequestDto (
        @NotNull
        LocalDate date,

        @NotNull
        @Schema(type = "string", format = "time", example = "01:30")
        LocalTime startTime,

        @NotNull
        @Schema(type = "string", format = "time", example = "02:00")
        LocalTime endTime,

        @NotNull
        int duration,

        @NotNull
        int maxApplicants,

        @NotNull
        String location

){
}
