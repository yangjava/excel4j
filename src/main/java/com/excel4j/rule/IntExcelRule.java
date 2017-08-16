package com.excel4j.rule;

import com.excel4j.utils.RuleUtils;


public class IntExcelRule implements ExcelRule<Integer>{

	@Override
	public void check(Object value, String columnName, String fieldName)
			throws Exception {
		
	}

	@Override
	public Integer readFilter(Object value, String columnName, String fieldName) {
   	   String  strField=(String) value;
   	    strField = RuleUtils.matchDoneBigDecimal(strField);
        strField = RuleUtils.converNumByReg(strField);
		return Integer.valueOf(strField);
	}
	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		return String.valueOf(value);
	}
}
