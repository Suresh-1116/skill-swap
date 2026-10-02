package com.suresh.skillSwap.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPatchRequest {
    private String memberName;
    @Email
    private String email;
    @Size(max = 200)
    private String bio;
    @Size(max = 100)
    private String location;
}
