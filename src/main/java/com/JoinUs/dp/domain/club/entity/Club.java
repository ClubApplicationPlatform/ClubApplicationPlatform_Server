package com.JoinUs.dp.domain.club.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Club {
    protected Club() {}

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    public Club(String name) {
        this.name = name;
    }
}
