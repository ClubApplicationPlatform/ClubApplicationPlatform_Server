// src/main/java/com/JoinUs/dp/global/domain/application/entity/ClubStatus.java
package com.JoinUs.dp.global.domain.application.entity;

public enum ClubStatus {
    PENDING,      // 심사중(신규)
    PASSED,       // 합격 (확정 대기)
    FAILED,       // 불합격
    CONFIRMED,    // 합격자 최종 확정
    DECLINED      // 합격자 철회
}

