package com.excel4j.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleUtils {
	
	
	public static String matchDoneBigDecimal(String bigDecimal){
        // 对科学计数法进行处理
        boolean flg = Pattern.matches("^-?\\d+(\\.\\d+)?(E-?\\d+)?$", bigDecimal);
        if (flg) {
            BigDecimal bd = new BigDecimal(bigDecimal);
            bigDecimal = bd.toPlainString();
        }
        return bigDecimal;
    }
    
    public static String converNumByReg(String number){
        Pattern compile = Pattern.compile("^(\\d+)(\\.0*)?$");
        Matcher matcher = compile.matcher(number);
        while (matcher.find()){
            number = matcher.group(1);
        }
        return number;
    }
}
