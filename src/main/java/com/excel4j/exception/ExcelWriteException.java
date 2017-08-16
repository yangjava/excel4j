package com.excel4j.exception;

public class ExcelWriteException extends Exception{
	
	
	private static final long serialVersionUID = -483100370912554135L;

	public ExcelWriteException() {
    }

    public ExcelWriteException(String message) {
        super(message);
    }

    public ExcelWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
