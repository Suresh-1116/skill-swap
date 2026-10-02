package com.suresh.skillSwap.exceptions;

public class SkillNotFoundException extends RuntimeException{
    public SkillNotFoundException(Long skillId){
        super("Skill Not found at ID: " +skillId);
    }
}
