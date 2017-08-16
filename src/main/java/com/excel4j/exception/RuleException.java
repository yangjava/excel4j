package com.excel4j.exception;

public class RuleException extends Exception{
	
	private static final long serialVersionUID = -8489803584593187158L;

    public RuleException() {

    }

    public RuleException(String message) {
        super(message);
    }

    public RuleException(String message, Throwable cause) {
        super(message, cause);
    }
    

}
