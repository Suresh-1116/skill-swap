package com.suresh.skillSwap.entities;

import com.suresh.skillSwap.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill {
    @Id
    @GeneratedValue
    private Long skillId;

    private String skillName;
    private String category;
    @Enumerated(EnumType.STRING)
    private SkillLevel level;
    private String description;
    private LocalDate createdAt;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;


    public Skill(String skillName,
                 String category,
                 SkillLevel level,
                 String description,
                 LocalDate createdAt,
                 Member member){
        this.skillName = skillName;
        this.category = category;
        this.level = level;
        this.description = description;
        this.createdAt = createdAt;
        this.member = member;
    }
}
