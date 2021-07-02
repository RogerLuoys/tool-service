package com.luoys.upgrade.toolservice.common;


import com.alibaba.fastjson.JSON;

public class StringUtil {

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

}
