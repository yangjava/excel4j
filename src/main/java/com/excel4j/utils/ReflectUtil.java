package com.excel4j.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.excel4j.exception.BeanRefException;

/*
 * 用于反射的工具类
 */
public class ReflectUtil {
	
	/**
	 * 获取类的实例
	 * @param clazz
	 * @return
	 * @throws BeanRefException
	 */
	public static <T> T newInstance(Class<T> clazz) throws BeanRefException {
		if (clazz.isInterface()) {
			throw new BeanRefException("Specified class is an interface"+clazz);
		}
		try {
			return clazz.newInstance();
		}
		catch (InstantiationException ex) {
			throw new BeanRefException("Is it an abstract class?", ex);
		}
		catch (IllegalAccessException ex) {
			throw new BeanRefException("Is the constructor accessible?"+clazz, ex);
		}
	}
	
	public  static Field[] getDeclaredFields(Class<?> clazz) {
		return  clazz.getDeclaredFields();
	}
	
	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Class<?> searchType = clazz;
		while (Object.class != searchType && searchType != null) {
			Field[] fields = getDeclaredFields(searchType);
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) &&
						(type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	public static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, null);
	}
	/**
	 * 获取字段
	 * @param clazz
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Field getField(Class<?> clazz,String name){
		return findField(clazz, name);
		
	}
    /** 
     * 对给定对象obj的propertyName指定的成员变量进行赋值 
     * 赋值为value所指定的值 
     * 该方法可以访问私有成员 
     */  
    public static void setProperty(Object obj, String propertyName, Object value) throws Exception  
    {  
        Class<?> clazz = obj.getClass();  
        Field field = clazz.getDeclaredField(propertyName);  
        //赋值前将该成员变量的访问权限打开  
        field.setAccessible(true);  
        field.set(obj, value);  
        //赋值后将该成员变量的访问权限关闭  
        field.setAccessible(false);  
    }
    
	/**
	 * 获取私有属性Value
	 * @param bean
	 * @param name
	 * @return
	 */
	public static Object getProperty(Object bean, String name){
		try {
			Field field = getField(bean.getClass(), name);
			if(field!=null){
				field.setAccessible(true);
				return field.get(bean);
			}else{
				throw new RuntimeException("The field [ "+field+"] in ["+bean.getClass().getName()+"] not exists");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获取常量值
	 * @param clazz 常量类
	 * @param constName 常量名称
	 * @return 常量对应的值
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getConstValue(Class<?> clazz,String constName){
		Field field = ReflectUtil.getField(clazz, constName);
		if(field!=null){
			field.setAccessible(true);
	    	try {
	    		Object object = field.get(null);
	    		if(object != null){
	    			return (T) object;
	    		}
	    		return null;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	/**
	 * 获取指定类的所有字段,排除static,final字段
	 * @param clazz 类型
	 * @return List<字段>
	 */
	public static List<Field> getFields(Class<?> clazz){
		List<Field> fieldResult = new ArrayList<Field>();
		while(clazz!=Object.class){
			try {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field:fields) {
					int modifiers = field.getModifiers();
					//过滤static或final字段
					if(Modifier.isStatic(modifiers)||Modifier.isFinal(modifiers)){
						continue;
					}
					fieldResult.add(field);
				}
			} catch (Exception ignore) {}
			clazz = clazz.getSuperclass();
		}
		return fieldResult;
	}
	
	
	/**
	 * 获取指定类的所有字段名称,排除static,final字段
	 * @param clazz 类型
	 * @return List<字段名称>
	 */
	public static List<String> getFieldNames(Class<?> clazz){
		List<Field> fields = getFields(clazz);
		List<String> fieldNames = new ArrayList<String>(fields.size());
		for(Field field:fields){
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}
	
	/**
	 * 通过反射, 获得定义 Class 时声明的父类的泛型参数的类型
	 * 如: public EmployeeDao extends BaseDao<Employee, String>
	 * @param clazz
	 * @param index
	 * @return
	 */
	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index){
		Type genType = clazz.getGenericSuperclass();
		
		if(!(genType instanceof ParameterizedType)){
			return Object.class;
		}
		Type [] params = ((ParameterizedType)genType).getActualTypeArguments();
		if(index >= params.length || index < 0){
			return Object.class;
		}
		if(!(params[index] instanceof Class)){
			return Object.class;
		}
		return (Class<?>) params[index];
	}
	
	/**
	 * 通过反射, 获得 Class 定义中声明的父类的泛型参数类型
	 * 如: public EmployeeDao extends BaseDao<Employee, String>
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> Class<T> getSuperGenericType(Class<?> clazz){
		return (Class<T>) getSuperClassGenricType(clazz, 0);
	}
	

}
