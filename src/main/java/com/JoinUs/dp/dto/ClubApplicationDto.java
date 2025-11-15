package com.JoinUs.dp.dto;

import java.time.LocalDate;
import java.util.List;

import com.JoinUs.dp.entity.ClubApplication;
import com.JoinUs.dp.entity.ClubStatus;
import com.JoinUs.dp.entity.QuestionAnswer;

public class ClubApplicationDto {
    private Long id;                        
    private Long clubId;                    
    private Long departmentId;              
    private String applicantName;           
    private String clubName;                
    private LocalDate appliedDate;          
    private List<QuestionAnswer> questionAnswers; 
    private ClubStatus status;              
    private String resultMessage;           

    public ClubApplicationDto() {}

    public ClubApplicationDto(Long id, Long clubId, Long departmentId, String applicantName, String clubName,
                              LocalDate appliedDate, List<QuestionAnswer> questionAnswers,
                              ClubStatus status, String resultMessage) {
        this.id = id;
        this.clubId = clubId;
        this.departmentId = departmentId;
        this.applicantName = applicantName;
        this.clubName = clubName;
        this.appliedDate = appliedDate;
        this.questionAnswers = questionAnswers;
        this.status = status;
        this.resultMessage = resultMessage;
    }

    public static ClubApplicationDto from(ClubApplication e) {
        return new ClubApplicationDto(
                e.getId(), e.getClubId(), e.getDepartmentId(), e.getApplicantName(), e.getClubName(),
                e.getAppliedDate(), e.getQuestionAnswers(), e.getStatus(), e.getResultMessage()
        );
    }

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
