package com.suresh.skillSwap.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private Long memberId;
    private String memberName;
    private String email;

    private String bio;
    private String location;
    private LocalDate createdAt;

    @OneToMany(mappedBy = "member")
    private List<Skill> skills;

    public Member(String memberName, String email, String bio, String location, LocalDate createdAt){
        this.memberName = memberName;
        this.email = email;
        this.bio = bio;
        this.location = location;
        this.createdAt = createdAt;
    }

}
