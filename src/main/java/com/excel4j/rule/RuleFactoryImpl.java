package com.excel4j.rule;

import java.sql.Timestamp;
import java.util.Date;

import com.excel4j.annotation.ExcelField;



public class RuleFactoryImpl implements RuleFactory{
	
	
    /**
     *  获取校验规则的类
     */
	@Override
	public ExcelRule getInstance(Class clazz,ExcelField  excelField) throws Exception {
        Class rule=excelField.rule();
        //如果没有校验规则,使用默认规则
        if(rule==NoExcelRule.class){
        	
            if ((Integer.class == clazz) || (int.class == clazz)
            		) {
                return new IntExcelRule();
            }
            if ((String.class == clazz)) {
                return new StringExcelRule();
            }
            if ((Long.class == clazz) || (long.class == clazz)) {
                return new LongExcelRule();
            }
            if ((Float.class == clazz) || (float.class == clazz)) {
                return new FloatExcelRule();
            }
            if ((Double.class == clazz) || (Double.class == clazz)) {
                return new DoubleExcelRule();
            }
            if ((Character.class == clazz) || (char.class == clazz)) {
                return new CharExcelRule();
            }
            if ((Date.class == clazz) ) {
                return new DateExcelRule();
            }
            if ((Timestamp.class == clazz) ) {
                return new TimestampExcelRule();
            }
        }else{
        	return (ExcelRule) rule.newInstance();
        }
        throw new Exception("Cannot Instance ExcelRule Class "+rule);
	}


}
