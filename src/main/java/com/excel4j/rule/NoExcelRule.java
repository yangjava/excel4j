package com.excel4j.rule;


/**
 * 

 */
public class NoExcelRule implements ExcelRule<Object> {

    public void check(Object value, String columnName, String fieldName) {
    
    }

    public Object readFilter(Object value, String columnName, String fieldName) {
    	
        return value;
    }
    
	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		return String.valueOf(value);
	}
}
