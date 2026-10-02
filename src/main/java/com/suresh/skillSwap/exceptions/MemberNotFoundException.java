package com.suresh.skillSwap.exceptions;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(Long memberId){
        super("Member Not Found at ID: " + memberId);
    }
}
