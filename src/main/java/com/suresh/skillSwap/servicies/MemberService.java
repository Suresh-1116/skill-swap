package com.suresh.skillSwap.servicies;

import com.suresh.skillSwap.dto.MemberPatchRequest;
import com.suresh.skillSwap.dto.MemberRequest;
import com.suresh.skillSwap.dto.MemberResponse;
import com.suresh.skillSwap.entities.Member;
import com.suresh.skillSwap.exceptions.MemberNotFoundException;
import com.suresh.skillSwap.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    final private MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /* ========> MEMBER SERVICE LAYER <=======+ */



    //POST A MEMEBER TO DB
    public MemberResponse createMember(MemberRequest memberRequest){
        Member member = memberRepository.save(new Member(memberRequest.getMemberName(),
                                                        memberRequest.getEmail(),
                                                        memberRequest.getBio(),
                                                        memberRequest.getLocation(),
                                                        LocalDate.now()));
        return toResponse(member);
    }


    //GET ALL MEMBERS FROM DB
    public List<MemberResponse> getMembers(){
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> responseList = new ArrayList<>();
        for(Member member: members) {
            responseList.add(toResponse(member));
        }

        return responseList;
    }


    //GET A MEMBER FROM DB BY ID
    public MemberResponse getMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

        return toResponse(member);
    }


    //UPDATE A MEMEBR IN DB
    public MemberResponse updateMember(Long memberId, MemberRequest memberRequest){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        member.setMemberName(memberRequest.getMemberName());
        member.setEmail(memberRequest.getEmail());
        member.setBio(memberRequest.getBio());
        member.setLocation(memberRequest.getLocation());


        return toResponse(memberRepository.save(member));
    }


    //PARTIAL UPDATE A MEMEBER IN DB
    public MemberResponse partialUpdateMember(Long memberId, MemberPatchRequest memberPatchRequest){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

        if(memberPatchRequest.getMemberName() != null){
            member.setMemberName(memberPatchRequest.getMemberName());
        }

        if(memberPatchRequest.getEmail() != null){
            member.setEmail(memberPatchRequest.getEmail());
        }
        if(memberPatchRequest.getBio() != null){
            member.setBio(memberPatchRequest.getBio());
        }

        if(memberPatchRequest.getLocation() != null){
            member.setLocation(memberPatchRequest.getLocation());
        }

        return toResponse(memberRepository.save(member));
    }


    //DELETE A MEMBER IN A DB
    public void deleteMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        memberRepository.delete(member);
    }






    public MemberResponse toResponse(Member member){
        return new MemberResponse(member.getMemberId(),
                member.getMemberName(),
                member.getEmail(),
                member.getBio(),
                member.getLocation(),
                member.getCreatedAt());
    }
}
