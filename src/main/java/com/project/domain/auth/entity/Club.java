package com.project.domain.stubs;
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

    public Club(Long id, String name, String type, String category, String department) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
        this.department = department;
    }
}