package com.suresh.skillSwap.servicies;

import com.suresh.skillSwap.dto.ExchangeRequestDto;
import com.suresh.skillSwap.dto.ExchangeRequestPatchDto;
import com.suresh.skillSwap.dto.ExchangeRequestResponse;
import com.suresh.skillSwap.entities.ExchangeRequest;
import com.suresh.skillSwap.entities.Member;
import com.suresh.skillSwap.entities.Skill;
import com.suresh.skillSwap.enums.RequestStatus;
import com.suresh.skillSwap.exceptions.ExchangeRequestNotFoundException;
import com.suresh.skillSwap.exceptions.MemberNotFoundException;
import com.suresh.skillSwap.exceptions.SkillNotFoundException;
import com.suresh.skillSwap.repositories.ExchangeRequestRepository;
import com.suresh.skillSwap.repositories.MemberRepository;
import com.suresh.skillSwap.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRequestService {
    final private ExchangeRequestRepository exchangeRequestRepository;
    final private MemberRepository memberRepository;
    final private SkillRepository skillRepository;

    public ExchangeRequestService(ExchangeRequestRepository exchangeRequestRepository,
                                     MemberRepository memberRepository,
                                     SkillRepository skillRepository){
        this.exchangeRequestRepository = exchangeRequestRepository;
        this.memberRepository = memberRepository;
        this.skillRepository = skillRepository;
    }

    /* ============> Exchange Request Servce Layer <=============== */


    //POST A EXCHANGE REQUEST TO THE DB
    public ExchangeRequestResponse createExchangeRequest(ExchangeRequestDto exchangeRequestDto){
        Member sender = memberRepository.findById(exchangeRequestDto.getSenderId()).orElseThrow(()
                                                            -> new MemberNotFoundException(exchangeRequestDto.getSenderId()));
        Skill offeredSkill = skillRepository.findById(exchangeRequestDto.getOfferedSkillId()).orElseThrow(()
                                                            -> new SkillNotFoundException(exchangeRequestDto.getOfferedSkillId()));

        List<Skill> skills = sender.getSkills();
        int count = 0;
        if(skills == null || skills.isEmpty()) {
            throw new SkillNotFoundException(exchangeRequestDto.getOfferedSkillId());
        }
        for (Skill skill : skills) {
            if (skill.getSkillId().equals(exchangeRequestDto.getOfferedSkillId())) {
                break;
            }
            count++;
            if (count == skills.size()) {
                throw new SkillNotFoundException(exchangeRequestDto.getOfferedSkillId());
            }
        }


        Member receiver = memberRepository.findById(exchangeRequestDto.getReceiverId()).orElseThrow(()
                                                                -> new MemberNotFoundException(exchangeRequestDto.getReceiverId()));
        Skill requestedSkill = skillRepository.findById(exchangeRequestDto.getRequestedSkillId()).orElseThrow(()
                                                        -> new SkillNotFoundException(exchangeRequestDto.getRequestedSkillId()));

        skills = receiver.getSkills();
        count = 0;
        if(skills == null || skills.isEmpty()) {
            throw new SkillNotFoundException(exchangeRequestDto.getRequestedSkillId());
        }

        for (Skill skill : skills) {
            if (skill.getSkillId().equals(exchangeRequestDto.getRequestedSkillId())) {
                break;
            }
            count++;
            if (count == skills.size()) {
                throw new SkillNotFoundException(exchangeRequestDto.getRequestedSkillId());
            }
        }



        return toResponse(exchangeRequestRepository.save(new ExchangeRequest(sender,
                                                                            receiver,
                                                                            offeredSkill,
                                                                            requestedSkill,
                                                                            exchangeRequestDto.getMessage(),
                                                                            RequestStatus.PENDING,
                                                                            LocalDate.now())));
    }



    //GET ALL THE EXCHNAGE REQUESTS FROM DB
    public List<ExchangeRequestResponse> getExchangeRequests(){
        List<ExchangeRequest> exchangeRequests = exchangeRequestRepository.findAll();
        List<ExchangeRequestResponse> exchangeRequestResponses = new ArrayList<>();
        for(ExchangeRequest exchangeRequest : exchangeRequests){
            exchangeRequestResponses.add(toResponse(exchangeRequest));
        }

        return exchangeRequestResponses;
    }



    //GET A EXCHANGE REQUEST FROM DB
    public ExchangeRequestResponse getExchangeRequest(Long requestId){
        ExchangeRequest exchangeRequest = exchangeRequestRepository.findById(requestId).orElseThrow(()
                -> new ExchangeRequestNotFoundException(requestId));

        return toResponse(exchangeRequest);
    }




    //UPDATE A EXCHANGE REQUEST TO THE DB
    public ExchangeRequestResponse updateExchangeRequest(Long requestId, ExchangeRequestDto exchangeRequestDto){
        ExchangeRequest exchangeRequest = exchangeRequestRepository.findById(requestId).orElseThrow(()
                                                            -> new ExchangeRequestNotFoundException(requestId));

        Member sender = memberRepository.findById(exchangeRequestDto.getSenderId()).orElseThrow(()
                -> new MemberNotFoundException(exchangeRequestDto.getSenderId()));
        exchangeRequest.setSender(sender);

        Skill offeredSkill = skillRepository.findById(exchangeRequestDto.getOfferedSkillId()).orElseThrow(()
                -> new SkillNotFoundException(exchangeRequestDto.getOfferedSkillId()));

        List<Skill> skills = sender.getSkills();
        if(skills == null || skills.isEmpty()) {
            throw new SkillNotFoundException(exchangeRequestDto.getOfferedSkillId());
        }
        int count = 0;
        for(Skill skill: skills){
            if(skill.getSkillId().equals(exchangeRequestDto.getOfferedSkillId())){
                break;
            }
            count++;
            if(count == skills.size()){
                throw new SkillNotFoundException(exchangeRequestDto.getOfferedSkillId());
            }
        }


        exchangeRequest.setOfferedSkill(offeredSkill);

        Member receiver = memberRepository.findById(exchangeRequestDto.getReceiverId()).orElseThrow(()
                -> new MemberNotFoundException(exchangeRequestDto.getReceiverId()));

        exchangeRequest.setReceiver(receiver);

        Skill requestedSkill = skillRepository.findById(exchangeRequestDto.getRequestedSkillId()).orElseThrow(()
                -> new SkillNotFoundException(exchangeRequestDto.getRequestedSkillId()));

        skills = receiver.getSkills();
        if(skills == null || skills.isEmpty()) {
            throw new SkillNotFoundException(exchangeRequestDto.getRequestedSkillId());
        }
        count = 0;
        for(Skill skill: skills){
            if(skill.getSkillId().equals(exchangeRequestDto.getRequestedSkillId())){
                break;
            }
            count++;
            if(count == skills.size()){
                throw  new SkillNotFoundException(exchangeRequestDto.getRequestedSkillId());
            }
        }

        exchangeRequest.setRequestedSkill(requestedSkill);

        exchangeRequest.setMessage(exchangeRequestDto.getMessage());

        return toResponse(exchangeRequestRepository.save(exchangeRequest));
    }




    //PATCH A EXCHANGE REQUEST TO THE DB
    public ExchangeRequestResponse partialUpdateExchangeRequest(Long requestId, ExchangeRequestPatchDto exchangeRequestPatchDto){
        ExchangeRequest exchangeRequest = exchangeRequestRepository.findById(requestId).orElseThrow(()
                -> new ExchangeRequestNotFoundException(requestId));
        List<Skill> skills = new ArrayList<>();
        int count = 0;

        if(exchangeRequestPatchDto.getSenderId() != null){
            Member sender = memberRepository.findById(exchangeRequestPatchDto.getSenderId()).orElseThrow(()
                            -> new MemberNotFoundException(exchangeRequestPatchDto.getSenderId()));

            if(exchangeRequestPatchDto.getOfferedSkillId() != null){
                Skill offeredSkill = skillRepository.findById(exchangeRequestPatchDto.getOfferedSkillId()).orElseThrow(()
                            -> new SkillNotFoundException(exchangeRequestPatchDto.getOfferedSkillId()));

                skills = sender.getSkills();
                if(skills == null || skills.isEmpty()) {
                    throw new SkillNotFoundException(exchangeRequestPatchDto.getOfferedSkillId());
                }
                count = 0;
                for(Skill skill: skills){
                    if(skill.getSkillId().equals(exchangeRequestPatchDto.getOfferedSkillId())){
                        break;
                    }
                    count++;
                    if(count == skills.size()){
                        throw new SkillNotFoundException(exchangeRequestPatchDto.getOfferedSkillId());
                    }
                }

                exchangeRequest.setOfferedSkill(offeredSkill);
            }
            else{
                skills = sender.getSkills();
                if(skills == null || skills.isEmpty()) {
                    throw new SkillNotFoundException(exchangeRequest.getOfferedSkill().getSkillId());
                }
                count = 0;
                for(Skill skill: skills){
                    if(skill.getSkillId().equals(exchangeRequest.getOfferedSkill().getSkillId())){
                        break;
                    }
                    count++;
                    if(count == skills.size()){
                        throw new SkillNotFoundException(exchangeRequest.getOfferedSkill().getSkillId());
                    }
                }
            }
            exchangeRequest.setSender(sender);
        }

        if(exchangeRequestPatchDto.getOfferedSkillId() != null){
            Skill offeredSkill = skillRepository.findById(exchangeRequestPatchDto.getOfferedSkillId()).orElseThrow(()
                    -> new SkillNotFoundException(exchangeRequestPatchDto.getOfferedSkillId()));

            skills = exchangeRequest.getSender().getSkills();
            if(skills == null || skills.isEmpty()) {
                throw new SkillNotFoundException(exchangeRequestPatchDto.getOfferedSkillId());
            }
            count = 0;
            for(Skill skill: skills){
                if(skill.getSkillId().equals(exchangeRequestPatchDto.getOfferedSkillId())){
                    break;
                }
                count++;
                if(count == skills.size()){
                    throw new SkillNotFoundException(exchangeRequestPatchDto.getOfferedSkillId());
                }
            }

            exchangeRequest.setOfferedSkill(offeredSkill);
        }

        if(exchangeRequestPatchDto.getReceiverId() != null){
            Member receiver = memberRepository.findById(exchangeRequestPatchDto.getReceiverId()).orElseThrow(()
                    -> new MemberNotFoundException(exchangeRequestPatchDto.getReceiverId()));

            if(exchangeRequestPatchDto.getRequestedSkillId() != null){
                Skill requestedSkill = skillRepository.findById(exchangeRequestPatchDto.getRequestedSkillId()).orElseThrow(()
                        -> new SkillNotFoundException(exchangeRequestPatchDto.getRequestedSkillId()));

                skills = receiver.getSkills();
                count = 0;
                if(skills == null || skills.isEmpty()) {
                    throw new SkillNotFoundException(exchangeRequestPatchDto.getRequestedSkillId());
                }
                for(Skill skill: skills){
                    if(skill.getSkillId().equals(exchangeRequestPatchDto.getRequestedSkillId())){
                        break;
                    }
                    count++;
                    if(count == skills.size()){
                        throw new SkillNotFoundException(exchangeRequestPatchDto.getRequestedSkillId());
                    }
                }

                exchangeRequest.setRequestedSkill(requestedSkill);
            }
            else{
                skills = receiver.getSkills();
                if(skills == null || skills.isEmpty()) {
                    throw new SkillNotFoundException(exchangeRequest.getRequestedSkill().getSkillId());
                }
                count = 0;
                for(Skill skill: skills){
                    if(skill.getSkillId().equals(exchangeRequest.getRequestedSkill().getSkillId())){
                        break;
                    }
                    count++;
                    if(count == skills.size()){
                        throw new SkillNotFoundException(exchangeRequest.getRequestedSkill().getSkillId());
                    }
                }
            }
            exchangeRequest.setReceiver(receiver);
        }

        if(exchangeRequestPatchDto.getRequestedSkillId() != null){
            Skill requestedSkill = skillRepository.findById(exchangeRequestPatchDto.getRequestedSkillId()).orElseThrow(()
                    -> new SkillNotFoundException(exchangeRequestPatchDto.getRequestedSkillId()));

            skills = exchangeRequest.getReceiver().getSkills();
            if(skills == null || skills.isEmpty()) {
                throw new SkillNotFoundException(exchangeRequestPatchDto.getRequestedSkillId());
            }
            count = 0;
            for(Skill skill: skills){
                if(skill.getSkillId().equals(exchangeRequestPatchDto.getRequestedSkillId())){
                    break;
                }
                count++;
                if(count == skills.size()){
                    throw new SkillNotFoundException(exchangeRequestPatchDto.getRequestedSkillId());
                }
            }

            exchangeRequest.setRequestedSkill(requestedSkill);
        }


        if(exchangeRequestPatchDto.getMessage() != null){
            exchangeRequest.setMessage(exchangeRequestPatchDto.getMessage());
        }

        if(exchangeRequestPatchDto.getStatus() != null){
            exchangeRequest.setStatus(exchangeRequestPatchDto.getStatus());
        }


        return toResponse(exchangeRequestRepository.save(exchangeRequest));
    }



    //DELETE A EXCHANGE REQUEST
    public void deleteExchangeRequest(Long requestId){
        ExchangeRequest exchangeRequest = exchangeRequestRepository.findById(requestId).orElseThrow(()
                                            -> new ExchangeRequestNotFoundException(requestId));

        exchangeRequestRepository.delete(exchangeRequest);
    }

    public ExchangeRequestResponse toResponse(ExchangeRequest exchangeRequest){
        return new ExchangeRequestResponse(exchangeRequest.getRequestId(),
                exchangeRequest.getSender().getMemberId(),
                exchangeRequest.getReceiver().getMemberId(),
                exchangeRequest.getOfferedSkill().getSkillId(),
                exchangeRequest.getRequestedSkill().getSkillId(),
                exchangeRequest.getMessage(),
                exchangeRequest.getStatus(),
                exchangeRequest.getCreatedAt()
                );
    }
}
