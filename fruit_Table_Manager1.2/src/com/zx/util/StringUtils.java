package com.zx.util;

/**
 * @ClassName StringUtils
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/13 8:10
 * @Version 1.0
 */
public class StringUtils {
    private static boolean isEmpty(String string) {
        //为null或者空字符串
        if (string == null || "".equals(string)) {
            return true;
        }
        //不为空返回false
        return false;
    }

    public static boolean isNotEmpty(String string) {
        //不为空返回true
        return !isEmpty(string);
    }
}
