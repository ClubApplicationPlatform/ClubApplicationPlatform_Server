package com.JoinUs.dp.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clubs")
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Club {
    @Id
    private Long id;
    private String name;
    private String type; 
    private String category;
    private String department;

    
}