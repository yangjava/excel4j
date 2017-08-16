package com.excel4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.excel4j.annotation.ExcelField;
import com.excel4j.excel.ExcelHeader;
import com.excel4j.rule.ExcelRule;
import com.excel4j.rule.RuleFactory;
import com.excel4j.rule.RuleFactoryImpl;
import com.excel4j.utils.ExcelUtils;
import com.excel4j.utils.ReflectUtil;

public class Excel4j {

	/**
	 * 读取Excel,支持任何不规则的Excel文件, 外层List表示所有的数据行，内层List表示每行中的cell单元数据位置
	 * 假设获取一个Excel第三行第二个单元格的数据，例子代码： FileInputStream excelStream = new
	 * FileInputStream(path); List<List<Object>> list =
	 * ExcelUtil.readExcel(excelStream);
	 * System.out.println(list.get(2).get(1));//第三行第二列,索引行位置是2,列的索引位置是1
	 * 
	 * @param excelStream
	 *            Excel文件流
	 * @param sheetIndex
	 *            Excel-Sheet 的索引
	 * @return List<List<Object>>
	 * @throws Exception
	 */
	public static List<List<Object>> readExcel(InputStream excelStream,
			int sheetIndex) throws Exception {
		List<List<Object>> datas = new ArrayList<List<Object>>();
		Workbook workbook = WorkbookFactory.create(excelStream);
		// 只读取第一个sheet
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		int rows = sheet.getPhysicalNumberOfRows();
		for (int i = 0; i < rows; i++) {
			Row row = sheet.getRow(i);
			short cellNum = row.getLastCellNum();
			List<Object> item = new ArrayList<Object>(cellNum);
			for (int j = 0; j < cellNum; j++) {
				Cell cell = row.getCell(j);
				Object value = ExcelUtils.getCellValue(cell);
				item.add(value);
			}
			datas.add(item);
		}
		return datas;
	}

	/**
	 * 读取Excel,支持任何不规则的Excel文件,默认读取第一个sheet页
	 * 外层List表示所有的数据行，内层List表示每行中的cell单元数据位置 假设获取一个Excel第三行第二个单元格的数据，例子代码：
	 * FileInputStream excelStream = new FileInputStream(path);
	 * List<List<Object>> list = ExcelUtil.readExcel(excelStream);
	 * System.out.println(list.get(2).get(1));//第三行第二列,索引行位置是2,列的索引位置是1
	 * 
	 * @param excelStream
	 *            Excel文件流
	 * @return List<List<Object>>
	 * @throws Exception
	 */
	public static List<List<Object>> readExcel(InputStream excelStream)
			throws Exception {
		return readExcel(excelStream, 0);
	}

	/**
	 * 获取第一行头信息
	 * 
	 * @param sheet
	 * @param clazz
	 * @param start
	 * @return
	 */
	public static <T> Map<Integer, ExcelHeader> readExcelHeader(Sheet sheet,
			Class<T> clazz, int start) {
		Row row = sheet.getRow(start);
		if (row == null) {
			throw new RuntimeException("开始行序号" + start + "异常");
		}
		Map<Integer, ExcelHeader> maps = getHeaderMap(row, clazz);
		if (maps == null || maps.size() <= 0) {
			throw new RuntimeException("读取Excel头信息为空,请检查");
		}
		return maps;
	}

	/**
	 * 获取Excel数据,返回List
	 * 
	 * @param sheet
	 * @param clazz
	 * @param start
	 * @param limit
	 * @param maps
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> readData(Sheet sheet, Class<T> clazz, int start,
			int limit, Map<Integer, ExcelHeader> maps) throws Exception {
		int maxLine = Math.min(sheet.getLastRowNum(), start + limit);
		List<T> list = new ArrayList<T>();
		for (int i = start + 1; i <= maxLine; i++) {
			Row row = sheet.getRow(i);
			Object obj = readRow(clazz, row, maps);
			list.add((T) obj);
		}
		return list;
	}

	public static <T> Object readRow(Class<T> clazz, Row row,
			Map<Integer, ExcelHeader> maps) throws Exception {
		T obj = ReflectUtil.newInstance(clazz);
		for (Cell cell : row) {
			int columnIndex = cell.getColumnIndex();
			ExcelHeader header = maps.get(columnIndex);
			if (null == header) {
				continue;
			}
			String filed = header.getFieldName();
			String val = ExcelUtils.getCellValue(cell);
			// 进行数据校验和过滤
			ExcelField excelField = header.getExcelField();
			RuleFactory ruleFactory = new RuleFactoryImpl();
			ExcelRule ruleObj = ruleFactory.getInstance(header.getFiledClazz(),
					excelField);
			ruleObj.check(val, header.getColumnName(), filed);
			Object value = ruleObj.readFilter(val, header.getColumnName(),
					filed);
			ReflectUtil.setProperty(obj, filed, value);
		}
		return obj;
	}

	public static <T> List<T> readExcel(Sheet sheet, Class<T> clazz, int start,
			int limit) throws Exception {
		Map<Integer, ExcelHeader> maps = readExcelHeader(sheet, clazz, start);
		return readData(sheet, clazz, start, limit, maps);
	}

	/**
	 * 获取Workbook
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(String filePath) throws Exception {
		return WorkbookFactory.create(new File(filePath));
	}

	/**
	 * 获取Workbook
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(InputStream inputStream)
			throws Exception {
		return WorkbookFactory.create(inputStream);
	}

	/**
	 * 获取Sheet
	 * 
	 * @param workbook
	 * @param sheetIndex
	 * @return
	 */
	public static Sheet getSheet(Workbook workbook, int sheetIndex) {
		return workbook.getSheetAt(sheetIndex);
	}

	/**
	 * 获取Sheet
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 */
	public static Sheet getSheet(Workbook workbook, String sheetName) {
		return workbook.getSheet(sheetName);
	}

	/**
	 * Excel第一个sheet全部转为List
	 * 
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toList(String excelPath, Class<T> clazz)
			throws Exception {
		return toList(excelPath, clazz, 0, Integer.MAX_VALUE, 0);
	}

	/**
	 * Excel第sheetIndex个sheet全部转为List
	 * 
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toList(String excelPath, Class<T> clazz,
			int sheetIndex) throws Exception {
		return toList(excelPath, clazz, 0, Integer.MAX_VALUE, sheetIndex);
	}

	/**
	 * Excel名为sheetName的sheet全部转为List
	 * 
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toList(String excelPath, Class<T> clazz,
			String sheetName) throws Exception {
		return toList(excelPath, clazz, 0, Integer.MAX_VALUE, sheetName);
	}

	/**
	 * Excel第一个sheet全部转为List
	 * 
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toList(InputStream inputStream, Class<T> clazz)
			throws Exception {
		return toList(inputStream, clazz, 0, Integer.MAX_VALUE, 0);
	}

	/**
	 * Excel第sheetIndex个sheet全部转为List
	 * 
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toList(InputStream inputStream, Class<T> clazz,
			int sheetIndex) throws Exception {
		return toList(inputStream, clazz, 0, Integer.MAX_VALUE, sheetIndex);
	}

	/**
	 * Excel名为sheetName的sheet全部转为List
	 * 
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toList(InputStream inputStream, Class<T> clazz,
			String sheetName) throws Exception {
		return toList(inputStream, clazz, 0, Integer.MAX_VALUE, sheetName);
	}

	/**
	 * Excel转为List<POJO>
	 * 
	 * @param filePath
	 *            文件路径
	 * @param clazz
	 *            POJO类
	 * @param start
	 *            开始索引
	 * @param limit
	 *            限制条数
	 * @param sheetIndex
	 *            sheet索引
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toList(String filePath, Class<T> clazz,
			int start, int limit, int sheetIndex) throws Exception {
		Workbook workbook = getWorkbook(filePath);
		Sheet sheet = getSheet(workbook, sheetIndex);
		return readExcel(sheet, clazz, start, limit);
	}

	public static <T> List<T> toList(String filePath, Class<T> clazz,
			int start, int limit, String sheetName) throws Exception {
		Workbook workbook = getWorkbook(filePath);
		Sheet sheet = getSheet(workbook, sheetName);
		return readExcel(sheet, clazz, start, limit);
	}

	/**
	 * Excel转为List<POJO>
	 * 
	 * @param inputStream
	 *            流
	 * @param clazz
	 *            POJO类
	 * @param start
	 *            开始索引
	 * @param limit
	 *            限制条数
	 * @param sheetIndex
	 *            sheet索引
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toList(InputStream inputStream, Class<T> clazz,
			int start, int limit, int sheetIndex) throws Exception {
		Workbook workbook = getWorkbook(inputStream);
		Sheet sheet = getSheet(workbook, sheetIndex);
		return readExcel(sheet, clazz, start, limit);
	}

	public static <T> List<T> toList(InputStream inputStream, Class<T> clazz,
			int start, int limit, String sheetName) throws Exception {
		Workbook workbook = getWorkbook(inputStream);
		Sheet sheet = getSheet(workbook, sheetName);
		return readExcel(sheet, clazz, start, limit);
	}

	public static void writeHeader(Sheet sheet, Class clazz,
			boolean isWriteHeader, List<ExcelHeader> headers) {
		Row row = sheet.createRow(0);
		if (isWriteHeader) {
			// 写标题
			for (int i = 0; i < headers.size(); i++) {
				row.createCell(i).setCellValue(headers.get(i).getColumnName());
			}
		}
	}

	public static void writeData(List<?> data, Sheet sheet, Class clazz,
			List<ExcelHeader> headers) throws Exception
			 {
		for (int i = 0; i < data.size(); i++) {
			Row row = sheet.createRow(i + 1);
			sheet.setColumnWidth(i + 1, 0);
			Object obj = data.get(i);
			for (int j = 0; j < headers.size(); j++) {
				ExcelHeader header = headers.get(j);
                String fieldName=header.getFieldName();
                String columnName=header.getFieldName();
                
				ExcelField excelField = header.getExcelField();
				RuleFactory ruleFactory = new RuleFactoryImpl();
				ExcelRule excelRule = ruleFactory.getInstance(
						header.getFiledClazz(), excelField);
				
				Object value = ReflectUtil.getProperty(obj, fieldName);
				String cellValue = excelRule.writeFilter(value, columnName, fieldName);
				Cell cell=row.createCell(j);
//				cell.setCellStyle(workbook.createCellStyle());
				
						cell.setCellValue(
						cellValue);
			}
		}
	}

	static public void toExcel(List<?> data, Class<?> clazz,
			boolean isWriteHeader, String sheetName, boolean isXSSF,
			String filePath) throws Exception {
		FileOutputStream fos = new FileOutputStream(filePath);
		Workbook workbook;
		if (isXSSF) {
			workbook = new XSSFWorkbook();
		} else {
			workbook = new HSSFWorkbook();
		}
		Sheet sheet;
		if (null != sheetName && !"".equals(sheetName)) {
			sheet = workbook.createSheet(sheetName);
		} else {
			sheet = workbook.createSheet();
		}
		List<ExcelHeader> headers = readHeader(clazz);
		// 写头部
		writeHeader(sheet, clazz, isWriteHeader, headers);
		// 写数据
		writeData(data, sheet, clazz, headers);
		workbook.write(fos);
		fos.close();
		workbook.close();
	}

	public static List<ExcelHeader> readHeader(Class<?> clazz) {
		List<ExcelHeader> headers = new ArrayList<>();
		List<Field> fields = new ArrayList<>();
		Field[] declaredFields = ReflectUtil.getDeclaredFields(clazz);
		fields.addAll(Arrays.asList(declaredFields));
		for (Field field : fields) {
			// 是否使用ExcelField注解
			if (field.isAnnotationPresent(ExcelField.class)) {
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				headers.add(new ExcelHeader(excelField.title(), excelField
						.order(), field.getName(), excelField, field.getType()));
			}
		}
		Collections.sort(headers);
		return headers;
	}

	/**
	 * 获取索引和ExcelHeader信息
	 * 
	 * @param titleRow
	 * @param clz
	 * @return
	 */
	static public Map<Integer, ExcelHeader> getHeaderMap(Row titleRow, Class clz) {
		List<ExcelHeader> headers = readHeader(clz);
		Map<Integer, ExcelHeader> maps = new HashMap<>();
		for (Cell c : titleRow) {
			String title = c.getStringCellValue();
			for (ExcelHeader eh : headers) {
				if (eh.getColumnName().equals(title.trim())) {
					maps.put(c.getColumnIndex(), eh);
					break;
				}
			}
		}
		return maps;
	}

}
