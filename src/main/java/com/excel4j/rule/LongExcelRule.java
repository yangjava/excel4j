package com.excel4j.rule;

import com.excel4j.utils.RuleUtils;


/**
 * 默认字段处理规则
 *
 * 不对字段值进行任何处理
 *
 */
public class LongExcelRule implements ExcelRule<Long> {

    public void check(Object value, String columnName, String fieldName) {
    }

    public Long readFilter(Object value, String columnName, String fieldName) {
    	 String  strField=(String) value;
    	 strField = RuleUtils.matchDoneBigDecimal(strField);
         strField = RuleUtils.converNumByReg(strField);
         return Long.valueOf(strField);
    }
	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		return String.valueOf(value);
	}
}
