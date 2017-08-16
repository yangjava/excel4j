package com.excel4j.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excel4j.exception.RegexException;

/**
 * 正则匹配相关工具
 */
public class RegexUtils {
    /**
     * <p>判断内容是否匹配</p></br>
     * @param pattern 匹配目标内容
     * @param reg     正则表达式
     * @return 返回boolean
     */
    static
    public boolean isMatched(String pattern, String reg) {
        Pattern compile = Pattern.compile(reg);
        return compile.matcher(pattern).matches();
    }

    /**
     * <p>正则提取匹配到的内容</p>
     * <p>例如：</p>
     * </br>
     * @param pattern 匹配目标内容
     * @param reg     正则表达式
     * @param group   提取内容索引
     * @return 提取内容集合
     * @throws {@link RegexException}
     */
    static
    public List<String> match(String pattern, String reg, int group)
            throws RegexException {

        List<String> matchGroups = new ArrayList<>();
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(pattern);
        if (group > matcher.groupCount() || group < 0)
            throw new RegexException("Illegal match group :" + group);
        while (matcher.find()) {
            matchGroups.add(matcher.group(group));
        }
        return matchGroups;
    }

    /**
     * <p>正则提取匹配到的内容,默认提取索引为0</p>
     * <p>例如：</p>
     *
     * @param pattern 匹配目标内容
     * @param reg     正则表达式
     * @return 提取内容集合
     * @throws {@link RegexException}
     */
    static public String match(String pattern, String reg) {

        String match = null;
        try {
            List<String> matchs = match(pattern, reg, 0);
            if (null != matchs && matchs.size() > 0) {
                match = matchs.get(0);
            }
        } catch (RegexException e) {
            e.printStackTrace();
        }
        return match;
    }

}
