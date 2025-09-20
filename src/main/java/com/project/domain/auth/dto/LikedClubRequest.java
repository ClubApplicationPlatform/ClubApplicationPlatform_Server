package com.project.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikedClubRequest {
    @NotBlank(message = "클럽 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "클럽 타입은 필수입니다.")
    private String type;

    private String category;
    private String department;
}
