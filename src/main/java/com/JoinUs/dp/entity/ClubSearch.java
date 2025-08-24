package com.JoinUs.dp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Club")
public class ClubSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer memberCount;
    private Boolean recruiting;

    // Getter / Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getMemberCount() {
        return memberCount;
    }
    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Boolean getRecruiting() {
        return recruiting;
    }
    public void setRecruiting(Boolean recruiting) {
        this.recruiting = recruiting;
    }
}
