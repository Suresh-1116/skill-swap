package com.suresh.skillSwap.dto;


import com.suresh.skillSwap.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequestResponse {
    private Long requestId;
    private Long senderId;
    private Long receiverId;
    private Long offeredSkillId;
    private Long requestedSkillId;
    private String message;
    private RequestStatus status;
    private LocalDate createdAt;
}
