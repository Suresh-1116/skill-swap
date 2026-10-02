package com.suresh.skillSwap.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequestDto {
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    @NotNull
    private Long offeredSkillId;
    @NotNull
    private Long requestedSkillId;
    @Size(max = 200)
    private String message;
}
