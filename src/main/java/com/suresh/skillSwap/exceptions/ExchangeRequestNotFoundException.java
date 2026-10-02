package com.suresh.skillSwap.exceptions;

public class ExchangeRequestNotFoundException extends RuntimeException{
    public ExchangeRequestNotFoundException(Long requestId){
        super("Excahnge Request is invalid or not foound at ID: " +requestId);
    }
}
