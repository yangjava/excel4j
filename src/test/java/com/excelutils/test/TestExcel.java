package com.excelutils.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.excel4j.Excel4j;
// 需要转化日期格式
public class TestExcel {
	@Test
	public  void testToList() throws Exception {
		List<User> list;
		list = Excel4j.toList("E:/1.xlsx", User.class, 0, 3, 0);
		System.out.println(list.size());
	}
	
	@Test
	public  void testToExcel() throws Exception{
		User user1=new User(1, "1",new Date());
		User user2=new User(1, "2",new Date());
		List<User> data=new ArrayList<User>();
		data.add(user1);
		data.add(user2);
		Excel4j.toExcel(data, User.class, true, null, true, "E:/2.xlsx");
	}
}
