package com.JoinUs.dp.repository;

import com.JoinUs.dp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // username으로 유저 찾기
    Optional<User> findByUsername(String username);

    // email로 유저 찾기
    Optional<User> findByEmail(String email);

    // 학과별 유저 조회
    java.util.List<User> findByDepartment(String department);

    // 학년별 유저 조회
    java.util.List<User> findByGrade(int grade);
}