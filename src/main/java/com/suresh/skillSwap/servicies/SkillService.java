package com.suresh.skillSwap.servicies;

import com.suresh.skillSwap.dto.SkillPatchRequest;
import com.suresh.skillSwap.dto.SkillRequest;
import com.suresh.skillSwap.dto.SkillResponse;
import com.suresh.skillSwap.entities.Member;
import com.suresh.skillSwap.entities.Skill;
import com.suresh.skillSwap.exceptions.MemberNotFoundException;
import com.suresh.skillSwap.exceptions.SkillNotFoundException;
import com.suresh.skillSwap.repositories.MemberRepository;
import com.suresh.skillSwap.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SkillService {
    final private SkillRepository skillRepository;
    final private MemberRepository memberRepository;

    public SkillService(SkillRepository skillRepository, MemberRepository memberRepository){
        this.skillRepository = skillRepository;
        this.memberRepository = memberRepository;
    }

    /* =========> SKILL SERVICE LAYER <============= */



    //POST A SKILL TO DB
    public SkillResponse createSkill(SkillRequest skillRequest){
        Member member = memberRepository.findById(skillRequest.getMemberId()).orElseThrow(()
                -> new MemberNotFoundException(skillRequest.getMemberId()));

        return  toResponse(skillRepository.save(new Skill(skillRequest.getSkillName(),
                skillRequest.getCategory(),
                skillRequest.getLevel(),
                skillRequest.getDescription(),
                LocalDate.now(),
                member)));

    }


    //GET ALL THE SKILL FROM DB
    public List<SkillResponse> getSkills(){
        List<Skill> skills = skillRepository.findAll();
        List<SkillResponse> skillResponses = new ArrayList<>();

        for(Skill skill: skills){
            skillResponses.add(toResponse(skill));
        }

        return skillResponses;
    }


    //GET A SKILL FROM DB
    public SkillResponse getSkill(Long skillId){
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));

        return toResponse(skill);
    }


    //UPDATE A SKILL IN DB
    public SkillResponse updateSkill(Long skillId, SkillRequest skillRequest){
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));
        Member member = memberRepository.findById(skillRequest.getMemberId()).orElseThrow(() ->
                new MemberNotFoundException(skillRequest.getMemberId()));
        skill.setSkillName(skillRequest.getSkillName());
        skill.setCategory(skillRequest.getCategory());
        skill.setLevel(skillRequest.getLevel());
        skill.setDescription(skillRequest.getDescription());
        skill.setMember(member);

        return toResponse(skillRepository.save(skill));
    }


    //PARTIAL UPDATE A SKILL IN DB
    public SkillResponse partialUpdateSkill(Long skillId, SkillPatchRequest skillPatchRequest){
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));

        if(skillPatchRequest.getMemberId() != null){
            Member member = memberRepository.findById(skillPatchRequest.getMemberId()).orElseThrow(()
                    -> new MemberNotFoundException(skillPatchRequest.getMemberId()));
            skill.setMember(member);
        }
        if(skillPatchRequest.getSkillName() != null){
            skill.setSkillName(skillPatchRequest.getSkillName());
        }

        if(skillPatchRequest.getCategory() != null){
            skill.setCategory(skillPatchRequest.getCategory());
        }
        if(skillPatchRequest.getLevel() != null){
            skill.setLevel(skillPatchRequest.getLevel());
        }

        if(skillPatchRequest.getDescription() != null){
            skill.setDescription(skillPatchRequest.getDescription());
        }

        return toResponse(skillRepository.save(skill));
    }


    //DELETE A SKILL IN DB
    public void deleteSkill(Long skillId){
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));
        skillRepository.delete(skill);
    }





    public SkillResponse toResponse(Skill skill){
        return new SkillResponse(skill.getSkillId(),
                skill.getSkillName(),
                skill.getCategory(),
                skill.getLevel(),
                skill.getDescription(),
                skill.getCreatedAt(),
                skill.getMember().getMemberId());
    }
}
