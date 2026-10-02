package com.suresh.skillSwap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private Long memberId;
    private String memberName;
    private String email;

    private String bio;
    private String location;
    private LocalDate createdAt;
}
