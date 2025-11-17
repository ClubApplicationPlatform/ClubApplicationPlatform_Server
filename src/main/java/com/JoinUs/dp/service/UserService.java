package com.JoinUs.dp.service;

import com.JoinUs.dp.dto.MyApplicationResponse;
import com.JoinUs.dp.dto.UserResponse;
import com.JoinUs.dp.dto.UserUpdateRequest;
import com.JoinUs.dp.entity.Applications;
import com.JoinUs.dp.entity.User;
import com.JoinUs.dp.repository.ApplicationsRepository;
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
    private final ApplicationsRepository applicationsRepository;
    private final ClubRepository clubRepository;

    public UserResponse getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
                user.getUserId(),
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

    public UserResponse updateMyInfo(Long userId, UserUpdateRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getNickname() != null) user.setNickname(req.getNickname());
        if (req.getPhone() != null) user.setPhone(req.getPhone());
        if (req.getDepartment() != null) user.setDepartment(req.getDepartment());

        userRepository.save(user);

        return new UserResponse(
                user.getUserId(),
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

    public List<MyApplicationResponse> getMyApplications(Long userId) {
        List<Applications> apps = applicationsRepository.findByUserId(userId);

        return apps.stream().map(app -> {
            String clubName = clubRepository.findById(app.getClubId())
                    .map(club -> club.getName())
                    .orElse("Unknown");

            return new MyApplicationResponse(
                    app.getApplicationId(),
                    app.getClubId(),
                    clubName,
                    app.getResult(),
                    app.getConfirmStatus(),
                    app.getCreatedAt()
            );
        }).collect(Collectors.toList());
    }
}
