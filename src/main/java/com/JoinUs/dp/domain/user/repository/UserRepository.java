package com.JoinUs.dp.domain.user.repository;

import com.JoinUs.dp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // [FIX] 이메일 전용 조회/중복체크
    Optional<User> findByEmail(String email);   // [UPDATED - TEAM SCHEMA]
    boolean existsByEmail(String email);        // [UPDATED - TEAM SCHEMA]
}