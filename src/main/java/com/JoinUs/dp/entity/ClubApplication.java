// src/main/java/com/JoinUs/dp/global/domain/application/entity/ClubApplication.java
package com.JoinUs.dp.entity;

import java.time.LocalDate;
import java.util.List;

public class ClubApplication {
    private Long id;                        // 신청 ID
    private Long clubId;                    // 클럽 ID
    private Long departmentId;              // 학과 ID
    private String applicantName;           // 신청자 이름
    private String clubName;                // 클럽 이름
    private LocalDate appliedDate;          // 신청 날짜
    private List<QuestionAnswer> questionAnswers; // 질문-답변 목록
    private ClubStatus status;              // 신청 상태
    private String resultMessage;           // 심사 결과 안내 문구

    public ClubApplication() {}

    public Long getId() { return id; }
    public Long getClubId() { return clubId; }
    public Long getDepartmentId() { return departmentId; }
    public String getApplicantName() { return applicantName; }
    public String getClubName() { return clubName; }
    public LocalDate getAppliedDate() { return appliedDate; }
    public List<QuestionAnswer> getQuestionAnswers() { return questionAnswers; }
    public ClubStatus getStatus() { return status; }
    public String getResultMessage() { return resultMessage; }

    public void setId(Long id) { this.id = id; }
    public void setClubId(Long clubId) { this.clubId = clubId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    public void setClubName(String clubName) { this.clubName = clubName; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) { this.questionAnswers = questionAnswers; }
    public void setStatus(ClubStatus status) { this.status = status; }
    public void setResultMessage(String resultMessage) { this.resultMessage = resultMessage; }
}
