package com.JoinUs.dp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.JoinUs.dp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    // ✅ 이메일 존재 여부
    boolean existsByEmail(String email);

    // username으로 유저 찾기
    Optional<User> findByUsername(String username);

    // email로 유저 찾기
    Optional<User> findByEmail(String email);

    // 학과별 유저 조회
    java.util.List<User> findByDepartment(String department);

    // 학년별 유저 조회
    java.util.List<User> findByGrade(int grade);
}