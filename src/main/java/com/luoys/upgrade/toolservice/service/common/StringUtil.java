package com.luoys.upgrade.toolservice.service.common;


import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author luoys
 */
public class StringUtil {


    // 变量格式 ${name}, 1<=name长度<=20
    public static final String PARAM_REGEX = ".*\\$\\{[A-Za-z0-9]{1,20}}.*";


    /**
     * 判断字符串是否为空(org.apache.dubbo.common.utils的方法)
     * @param cs 目标字符串
     * @return 字符串为null || 长度为0 || 只包含空格，则返回true
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断所有字符串是否有空(org.apache.dubbo.common.utils的方法)
     * @param ss 多个字符串入参
     * @return 如果任意入参不为null且长度大于0，则返回false
     */
    public static boolean isNoneEmpty(final String... ss) {
        if (ss == null || ss.length ==0) {
            return false;
        } else {
            String[] var1 = ss;
            int var2 = ss.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String s = var1[var3];
                if (isEmpty(s)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static boolean isAnyEmpty(final String... ss) {
        return !isNoneEmpty(ss);
    }

    /**
     * 判断字符串是否为空
     * @param str 目标字符串
     * @return 字符串为null或长度为0，则返回true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 获取符合正则表达式的所有字符串
     * @param regex
     * @param target
     * @return
     */
    public static List<String> getMatch(String regex, String target) {
        Matcher matcher = Pattern.compile(regex).matcher(target);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

}
