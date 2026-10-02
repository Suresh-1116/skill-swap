package com.suresh.skillSwap.dto;

import com.suresh.skillSwap.enums.RequestStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequestPatchDto {

    private Long senderId;
    private Long receiverId;
    private Long offeredSkillId;
    private Long requestedSkillId;
    @Size(max = 200)
    private String message;
    private RequestStatus status;
}
