package com.excel4j.rule;


/**
 * 默认字段处理规则
 *
 * 不对字段值进行任何处理
 *
 */
public class StringExcelRule implements ExcelRule<String> {

    public void check(Object value, String columnName, String fieldName) {
    	
    }

    public String readFilter(Object value, String columnName, String fieldName) {
        return String.valueOf(value);
    }
	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		return String.valueOf(value);
	}
}
