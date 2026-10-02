package com.suresh.skillSwap.entities;

import com.suresh.skillSwap.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequest {
    @Id
    @GeneratedValue
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne
    @JoinColumn(name = "offered_skill_id")
    private Skill offeredSkill;

    @ManyToOne
    @JoinColumn(name = "requested_skill_id")
    private Skill requestedSkill;
    private String message;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private LocalDate createdAt;

    public ExchangeRequest(Member sender,
                           Member receiver,
                           Skill offeredSkill,
                           Skill requestedSkill,
                           String message,
                           RequestStatus status,
                           LocalDate createdAt){
        this.sender = sender;
        this.receiver = receiver;
        this.offeredSkill = offeredSkill;
        this.requestedSkill = requestedSkill;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }
}
