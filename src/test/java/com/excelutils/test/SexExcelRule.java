package com.excelutils.test;

import com.excel4j.rule.ExcelRule;


/**
 * 默认字段处理规则
 *
 * 不对字段值进行任何处理
 *
 */
public class SexExcelRule implements ExcelRule<String> {

    public void check(Object value, String columnName, String fieldName) {
    	
    }


	@Override
	public String readFilter(Object value, String columnName, String fieldName) {
		String sex=String.valueOf(value);
    	if("男".equals(sex)){
    		return "1";
    	}else{
    		return "0";
    	}
	}

	@Override
	public String writeFilter(Object value, String columnName, String fieldName) {
		if("1".equals(value)){
    		return "男";
    	}else{
    		return "女";
    	}
	}

}
