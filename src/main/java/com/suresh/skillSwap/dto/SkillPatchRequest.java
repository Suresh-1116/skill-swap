package com.suresh.skillSwap.dto;


import com.suresh.skillSwap.enums.SkillLevel;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillPatchRequest {
    private String skillName;
    private String category;
    private SkillLevel level;
    @Size(max = 500)
    private String description;

    private Long memberId;
}
