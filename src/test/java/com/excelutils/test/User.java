package com.excelutils.test;

import java.util.Date;

import com.excel4j.annotation.ExcelField;

public class User {
	
    @ExcelField(title="id",order=1)
	private int id;
    
    @ExcelField(title="姓名",order=2,rule=SexExcelRule.class)
	private String name;
    
    @ExcelField(title="日期",order=3)
    private Date date;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public User(int id, String name, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
	}

	public User() {
		super();
	}
	
	
	
}
