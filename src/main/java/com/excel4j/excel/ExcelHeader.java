package com.excel4j.excel;

import com.excel4j.annotation.ExcelField;

public class ExcelHeader implements Comparable<ExcelHeader> {

	/**
	 * Excel的列名
	 */

	private String columnName;

	/**
	 * Excel每列排序
	 */

	private int order;

	/**
	 * 每列对应的POJO的注解fieldName
	 */
	private String fieldName;

	/**
	 * 每列对应的POJO的注解fieldType
	 */
	private Class filedClazz;

	/**
	 * 每个field对应的注解
	 */
	private ExcelField excelField;

	public Class getFiledClazz() {
		return filedClazz;
	}

	public void setFiledClazz(Class filedClazz) {
		this.filedClazz = filedClazz;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int compareTo(ExcelHeader o) {
		return order - o.order;
	}

	public ExcelHeader(String columnName, int order, String fieldName,
			ExcelField excelField) {
		super();
		this.columnName = columnName;
		this.order = order;
		this.fieldName = fieldName;
		this.excelField = excelField;
	}

	public ExcelHeader(String columnName, int order, String fieldName,
			ExcelField excelField, Class filedClazz) {
		super();
		this.columnName = columnName;
		this.order = order;
		this.fieldName = fieldName;
		this.excelField = excelField;
		this.filedClazz = filedClazz;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public ExcelField getExcelField() {
		return excelField;
	}

	public void setExcelField(ExcelField excelField) {
		this.excelField = excelField;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
