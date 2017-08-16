package com.excel4j.rule;

import java.sql.Timestamp;
import java.util.Date;

import com.excel4j.utils.DateUtils;

public class TimestampExcelRule implements ExcelRule<Timestamp>{
	
	@Override
	public void check(Object value, String columnName, String fieldName)
			throws Exception {

	}

	@Override
	public Timestamp readFilter(Object value, String columnName, String fieldName) {
		Date date=DateUtils.str2DateUnmatch2Null((String)value);
		return new Timestamp(date.getTime());
	}
	
	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		return String.valueOf(value);
	}
}
