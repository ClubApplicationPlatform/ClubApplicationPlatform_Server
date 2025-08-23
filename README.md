[파일 역할 정리]
📌 공통 모듈

ApiPath.java → 모든 API 엔드포인트 경로를 상수로 정의 (공지/FAQ, 동아리 신청 등).
Response.java → 공통 응답 객체(상태 코드, 데이터, 메시지, 타임스탬프 포함).
NotFoundException.java → 데이터 미존재 시 예외 정의.
GlobalExceptionHandler.java → 예외를 공통적으로 처리(404/500 반환).
JwtProvider.java → JWT 스텁, "Bearer 이메일" 형식에서 이메일 추출.
SecurityConfiguration.java → Spring Security 설정 (CSRF, FormLogin 비활성화, 세션 정책).

📌 동아리 신청 (ClubApplication) 도메인

ClubApplication.java → 신청 엔티티 (신청자, 클럽, 학과, 질문답변, 상태 등).
ClubStatus.java → 신청 상태(PENDING, PASSED, FAILED, CONFIRMED, DECLINED).
QuestionAnswer.java → 지원자가 입력한 질문/답변 구조.
ClubApplicationDto.java → DTO (엔티티 ↔ 응답 변환).
ClubSummary.java → 학과별 클럽 요약 DTO (id, 이름).
ClubApplicationRepository.java → 인메모리 저장소(Map), CRUD, 클럽/학과별 조회 지원.
ClubApplicationService.java → 비즈니스 로직 (신청 등록/조회/수정/취소, 합격 여부, 확정/철회, 추가 합격).
ClubApplicationController.java → REST API 제공 (POST/GET/PUT/PATCH/DELETE).

📌 공지 / FAQ (Notice) 도메인

Notice.java → 공지 엔티티 (id, clubId, 제목, 내용, 작성일, 타입(FAQ/CLUB_NOTICE)).

NoticeResponse.java → 응답 DTO (엔티티 → 클라이언트 응답 변환).
NoticeCreateRequest.java → 공지/FAQ 등록 요청 DTO.
NoticeRepository.java → 인메모리 저장소(List), 샘플 데이터 4건 포함, FAQ/공지 CRUD.
NoticeService.java → 공지/FAQ 비즈니스 로직 (FAQ 목록, 상세조회, 등록, 클럽 공지).
NoticeController.java → REST API 제공 (FAQ 목록 조회, 공지 상세 조회, FAQ 등록).

📌 부트스트랩
DpApplication.java → Spring Boot 메인 실행 클래스.


[END POINT 기능 정리]

POST /api/applications
→ 신규 동아리 신청 등록

GET /api/applications
→ 전체 신청 목록 조회

GET /api/applications/{applicationId}
→ 특정 신청 상세 조회

DELETE /api/applications/{applicationId}
→ 신청 취소(삭제)

PUT /api/applications
→ 신청 정보 전체 수정

PATCH /api/applications/{applicationId}
→ 신청 정보 일부 수정

FAQ 관련

GET /api/notices
→ FAQ 목록 조회

GET /api/notices/{noticeId}
→ 공지 상세 조회 (FAQ/클럽공지 공통)

POST /api/notices
→ FAQ 등록
