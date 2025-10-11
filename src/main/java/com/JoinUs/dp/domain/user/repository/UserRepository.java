package com.JoinUs.dp.domain.user.repository;

import com.JoinUs.dp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    // 테스트 데이터 생성용 코드 (domain.interview.SetData 파일 삭제시 삭제해도됨)
    User findByUsername(String userName);
}
