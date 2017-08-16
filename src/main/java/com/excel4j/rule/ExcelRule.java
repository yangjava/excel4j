package com.excel4j.rule;


/**
 * 校验规则接口
 *
 * @param <T>
 */
public interface ExcelRule<T> {
    /**
     * 实现对单元格内容的检查
     * 如果内容不合法，则抛出ExcelContentInvalidException
     *
     * @param value
     * @param columnName
     * @param fieldName
     * @throws ExcelContentInvalidException
     */
    public void check(Object value, String columnName, String fieldName) throws Exception;

    /**
     * 内容过滤规则
     * 在该方法中对内容进行修改，并返回修改后的对象
     *
     * @param value
     * @param columnName
     * @param fieldName
     * @return
     */
    public T readFilter(Object value, String columnName, String fieldName);
    
    /**
     * 内容过滤规则
     * 在该方法中对内容进行修改，并返回修改后的对象
     *
     * @param value
     * @param columnName
     * @param fieldName
     * @return
     */
    public String writeFilter(Object value, String columnName, String fieldName);
}
