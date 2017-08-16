package com.excel4j.rule;

import java.util.Date;

import com.excel4j.utils.DateUtils;

public class DateExcelRule implements ExcelRule<Date> {

	@Override
	public void check(Object value, String columnName, String fieldName)
			throws Exception {

	}

	@Override
	public Date readFilter(Object value, String columnName, String fieldName) {
		return DateUtils.str2DateUnmatch2Null((String)value);
	}

	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		return DateUtils.date2Str((Date)value);
	}

}

