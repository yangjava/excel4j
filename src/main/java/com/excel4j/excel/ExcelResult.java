package com.excel4j.excel;

import java.util.List;

public class ExcelResult {
	
	
	private List<ExcelHeader> excelHeaderList;
	
    private List  ExcelDateList;

	public List<ExcelHeader> getExcelHeaderList() {
		return excelHeaderList;
	}

	public void setExcelHeaderList(List<ExcelHeader> excelHeaderList) {
		this.excelHeaderList = excelHeaderList;
	}

	public List getExcelDateList() {
		return ExcelDateList;
	}

	public void setExcelDateList(List excelDateList) {
		ExcelDateList = excelDateList;
	}
    
    

}
