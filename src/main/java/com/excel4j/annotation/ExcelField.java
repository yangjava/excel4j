package com.excel4j.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.excel4j.rule.ExcelRule;
import com.excel4j.rule.NoExcelRule;
/**
 * 关于Excel自定义注解
 * @author:杨京京
 * @QQ:1280025885
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelField {

    /**
     * 属性的标题名称
     */
    String title();

    /**
     * 在excel的顺序
     */
    int order() default 0;
    
	
	/**
	 * 字段归属组（根据分组导出导入）
	 */
	int[] groups() default {};
	
	
	/**
	 * 反射类型
	 */
	Class<?> fieldType() default Class.class;

	
	
	 @SuppressWarnings("rawtypes") Class<? extends ExcelRule> rule() default NoExcelRule.class;


	// 背景色,对齐方式,字体颜色
//	 /** cell的宽度,此属性不受enableStyle影响,如自己把数值写的过大,看org.apache.poi.ss.usermodel.Sheet.setColumnWidth(int, int)解决 */
//	 String columnWidth();
//		/** cell对其方式:支持,center,left,right */
//	 String align() ;
//		/** 标题cell背景色:看org.apache.poi.ss.usermodel.IndexedColors 可用颜色*/
//	 String titleBgColor();
//		/** 标题cell字体色:看org.apache.poi.ss.usermodel.IndexedColors 可用颜色*/
//	 String titleFountColor();



}