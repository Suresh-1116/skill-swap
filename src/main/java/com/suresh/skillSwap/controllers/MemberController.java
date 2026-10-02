package com.suresh.skillSwap.controllers;

import com.suresh.skillSwap.dto.MemberPatchRequest;
import com.suresh.skillSwap.dto.MemberRequest;
import com.suresh.skillSwap.dto.MemberResponse;
import com.suresh.skillSwap.servicies.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    final private MemberService memberService;
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    /* ====================> MEMBER CONTROLLER LAYER <=========== */
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest memberRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberRequest));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(){
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable(name = "id") Long memberId){
        return ResponseEntity.ok(memberService.getMember(memberId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable(name = "id") Long memberId,
                                                       @Valid @RequestBody MemberRequest memberRequest){
        return ResponseEntity.ok(memberService.updateMember(memberId, memberRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MemberResponse> partialUpdateMember(@PathVariable(name = "id") Long memberId,
                                                              @Valid @RequestBody MemberPatchRequest memberPatchRequest){
        return ResponseEntity.ok(memberService.partialUpdateMember(memberId, memberPatchRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Valid> deleteMember(@PathVariable(name = "id") Long memberId){
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}
