package com.excel4j.rule;

import com.excel4j.annotation.ExcelField;
import com.excel4j.excel.ExcelHeader;
import com.excel4j.exception.RuleException;


public interface RuleFactory {
	
	<T extends ExcelRule> T getInstance(Class clazz,ExcelField  excelField) throws Exception;

}
