package com.excel4j.utils;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtils {

	
    /**
     * 以不同的类型读取单元格的值,返回字符串
     * @param cell
     * @return
     */
    static
    public String getCellValue(Cell cell) {
        String value;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
            	value = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
            	value= String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
            	value = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
            	value= String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
            	value = cell.getStringCellValue();
                break;
            default:
            	value= null;
                break;
        }
        return value;
    }
}
