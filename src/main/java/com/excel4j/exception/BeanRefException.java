package com.excel4j.exception;

public class BeanRefException extends Exception{
	
	
	private static final long serialVersionUID = -1886015792135747556L;

	public BeanRefException() {

    }

    public BeanRefException(String message) {
        super(message);
    }

    public BeanRefException(String message, Throwable cause) {
        super(message, cause);
    }
}
