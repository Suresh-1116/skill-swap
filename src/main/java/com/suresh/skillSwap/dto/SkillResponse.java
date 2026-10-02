package com.suresh.skillSwap.dto;

import com.suresh.skillSwap.entities.Member;
import com.suresh.skillSwap.enums.SkillLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillResponse {
    private Long skillId;
    private String skillName;
    private String category;
    private SkillLevel level;
    private String description;
    private LocalDate createdAt;

    private Long memberId;

}
