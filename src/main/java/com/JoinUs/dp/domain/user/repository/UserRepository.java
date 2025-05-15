package com.JoinUs.dp.domain.user.repository;

import com.JoinUs.dp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
