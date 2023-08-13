package com.yzp.myssm.util;

public class StringUtil {
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmptyOrNull(String str) {
        return !isEmptyOrNull(str);
    }
}
