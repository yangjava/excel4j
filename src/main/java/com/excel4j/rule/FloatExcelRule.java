package com.excel4j.rule;

import com.excel4j.utils.RuleUtils;

public class FloatExcelRule implements ExcelRule<Float>{

	@Override
	public void check(Object value, String columnName, String fieldName)
			throws Exception {
		
	}

	@Override
	public Float readFilter(Object value, String columnName, String fieldName) {
   	    String  strField=(String) value;
   	    strField = RuleUtils.matchDoneBigDecimal(strField);
        strField = RuleUtils.converNumByReg(strField);
		return Float.valueOf(strField);
	}
	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		return String.valueOf(value);
	}
}
