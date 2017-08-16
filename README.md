## Excel4J 是基于 poi 的 excel 操作组件，大大减少代码量，提高开发效率。
使用注注解的形式完成导入导出。

一、jar包依赖

          <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.12</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.12</version>
        </dependency>
		
		
二、如何使用？参考

public class User {
	
    @ExcelField(title="id",order=1)
	private int id;
    
    @ExcelField(title="姓名",order=2,rule=SexExcelRule.class)
	private String name;
    
    @ExcelField(title="日期",order=3)
    private Date date;
	
}


一行代码实现Excel的导入导出


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

三 注册新的类型和数据校验


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

四  后续改进
1) 分组导出
2) 字体,颜色,大小等注解添加
