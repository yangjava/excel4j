package com.excel4j.rule;

public class CharExcelRule implements ExcelRule<Character> {

	@Override
	public void check(Object value, String columnName, String fieldName)
			throws Exception {

	}

	@Override
	public Character readFilter(Object value, String columnName, String fieldName) {
		return ((String)value).toCharArray()[0];
	}

	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		return String.valueOf(value);
	}

}

