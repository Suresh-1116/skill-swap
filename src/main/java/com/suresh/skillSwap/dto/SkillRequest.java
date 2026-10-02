package com.suresh.skillSwap.dto;


import com.suresh.skillSwap.enums.SkillLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillRequest {
    @NotBlank
    private String skillName;
    @NotBlank
    private String category;
    @NotNull
    private SkillLevel level;
    @Size(max = 500)
    private String description;

    @NotNull
    private Long memberId;
}
