package com.zx.util;

/**
 * @ClassName StringUtilsZ
 * @Description TODO
 * @Author xpower
 * @Date 2022/6/15 20:54
 * @Version 1.0
 */
public class StringUtilsZ {
    public static boolean isEmpty(String s) {
        if (s == null || "".equals(s)) {
            //字符为空
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

}
