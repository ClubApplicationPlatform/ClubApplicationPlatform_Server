package com.JoinUs.dp.service;

import com.JoinUs.dp.dto.ApplicationResponse;
import com.JoinUs.dp.dto.UserResponse;
import com.JoinUs.dp.dto.UserUpdateRequest;
import com.JoinUs.dp.entity.Application;
import com.JoinUs.dp.entity.Club;
import com.JoinUs.dp.entity.User;
import com.JoinUs.dp.repository.ApplicationRepository;
import com.JoinUs.dp.repository.ClubRepository;
import com.JoinUs.dp.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final ClubRepository clubRepository;

    /** 내 정보 조회 */
    public UserResponse getMyInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getNickname(),
                user.getDepartment(),
                user.getStudentId(),
                user.getPhone(),
                user.getRole().name(),
                user.getGrade()
        );
    }

    /** 내 정보 수정 */
    public UserResponse updateMyInfo(Long userId, UserUpdateRequest req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getNickname() != null) user.setNickname(req.getNickname());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getDepartment() != null) user.setDepartment(req.getDepartment());

        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getNickname(),
                user.getDepartment(),
                user.getStudentId(),
                user.getPhone(),
                user.getRole().name(),
                user.getGrade()
        );
    }

    /** 내 신청 목록 조회 */
    public List<ApplicationResponse> getMyApplications(Long userId) {

        List<Application> apps = applicationRepository.findByUserId(userId);

        return apps.stream()
                .map(app -> {

                    String clubName = clubRepository.findById(app.getClubId())
                            .map(Club::getName)
                            .orElse("Unknown");

                    return new ApplicationResponse(
                            app.getId(),
                            app.getClubId(),
                            clubName,
                            app.getStatus(),     // ✔ 단일 enum
                            app.getMessage(),    // ✔ 설명 메시지
                            app.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());
    }
}
