package com.excel4j.exception;

public class ExcelReadException extends Exception{
	
	private static final long serialVersionUID = -8489803584593187158L;

    public ExcelReadException() {

    }

    public ExcelReadException(String message) {
        super(message);
    }

    public ExcelReadException(String message, Throwable cause) {
        super(message, cause);
    }

}
