package com.JoinUs.dp.service;

import com.JoinUs.dp.common.exception.NotFoundException;
import com.JoinUs.dp.dto.ApplicationDto;
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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final ClubRepository clubRepository;

    /** 내 정보 조회 */
    public UserResponse getMyInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다. userId=" + userId));

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
                .orElseThrow(() -> new NotFoundException("해당 유저를 찾을 수 없습니다. userId=" + userId));

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

    /** 회원 생성 (관리자용) */
    public User createUser(User user) {
        // create 시에도 null 값, 중복 검증 추가하는 것이 권장됨 (필요 시 추가 가능)
        return userRepository.save(user);
    }

    /** 내 신청 목록 조회 */
    public List<ApplicationResponse> getMyApplications(Long userId) {

        // 🔥 유저 존재 여부 먼저 체크
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다. userId=" + userId);
        }

        List<Application> apps = applicationRepository.findByUserId(userId);

        return apps.stream()
                .map(app -> {
                    // 🔥 없는 클럽 조회 시 404가 맞음
                    Club club = clubRepository.findById(app.getClubId())
                            .orElseThrow(() ->
                                    new NotFoundException("해당 clubId는 존재하지 않습니다. clubId=" + app.getClubId())
                            );

                    ApplicationDto dto = ApplicationDto.from(app);

                    return new ApplicationResponse(
                            dto.getApplicationId(),
                            dto.getClubId(),
                            club.getName(),   // Unknown 제거
                            dto.getStatus(),
                            dto.getMessage(),
                            dto.getCreatedAt()
                    );
                })
                .toList();
    }
}
