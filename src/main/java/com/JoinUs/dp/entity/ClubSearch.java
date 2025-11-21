package com.JoinUs.dp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clubs") // ✅ 복수형으로 변경!
public class ClubSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id") // ✅ 실제 PK 컬럼명 일치
    private Long clubId;


    @Column(name = "name") // ✅ 명시 추가 (중요!)
    private String name;
    @Column(name = "member_count")
    private Integer memberCount;

    @Column(name = "recruiting")
    private Boolean recruiting;

    // ✅ Getter / Setter
    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getMemberCount() { return memberCount; }
    public void setMemberCount(Integer memberCount) { this.memberCount = memberCount; }

    public Boolean getRecruiting() { return recruiting; }
    public void setRecruiting(Boolean recruiting) { this.recruiting = recruiting; }
}
