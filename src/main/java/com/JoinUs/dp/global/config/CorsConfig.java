package com.JoinUs.dp.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로(/**)에 대해 CORS를 설정합니다.
        registry.addMapping("/**")

                // [필수] 허용할 Origin(도메인)을 지정합니다.
                // 1. 개발/테스트 환경용 로컬 프론트엔드 주소
                // 2. 렌더에 배포될 프론트엔드 주소 (예: https://your-frontend-project.onrender.com)
                // 3. (선택) Swagger UI가 접속되는 도메인 (일반적으로 백엔드와 동일하여 필요 없을 수 있으나, 명시적으로 추가 가능)
                // 아래는 예시이므로, 실제 프론트엔드 URL로 변경해야 합니다.
                .allowedOrigins(
                        "http://localhost:3000"
//                        ,
//                        "https://your-frontend-project.onrender.com"

                )

                // 허용할 HTTP Method를 지정합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")

                // 허용할 헤더를 지정합니다. (일반적으로 모든 헤더 허용)
                .allowedHeaders("*")

                // 사용자 인증 정보(쿠키, Authorization 헤더 등) 전송을 허용합니다.
                .allowCredentials(true)

                // Preflight 요청에 대한 캐시 시간을 지정합니다. (성능 최적화)
                .maxAge(3600);
    }
}