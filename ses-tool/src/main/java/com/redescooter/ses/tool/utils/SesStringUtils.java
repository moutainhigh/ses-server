package com.redescooter.ses.tool.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SesStringUtils extends StringUtils {

    public static String lowerFirst(String str) {
        if (SesStringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
    }

    public static String upperFirst(String str) {
        if (SesStringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 缩略字符串（替换html）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
    }


    /**
     * 转换为Double类型
     */
    public static Double toDouble(Object val) {
        if (val == null) {
            return 0D;
        }
        try {
            return Double.valueOf(trim(val.toString()));
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 转换为Float类型
     */
    public static Float toFloat(Object val) {
        return toDouble(val).floatValue();
    }

    /**
     * 转换为Long类型
     */
    public static Long toLong(Object val) {
        return toDouble(val).longValue();
    }

    /**
     * 转换为Integer类型
     */
    public static Integer toInteger(Object val) {
        return toLong(val).intValue();
    }

    /**
     * string  首位、中间去空格（会去除掉字符串中，所有空格）eg:06 67 32 22 12--> 0667322212
     *
     * @param val
     * @return
     */
    public static String stringTrim(String val) {
        return StringUtils.replace(StringUtils.trimToEmpty(val), " ", "");
    }

    /**
     * 手机号加密 （中间位数加密） 默认前二后四显示 并格式化输出 eg: 06 67 32 22 12 --->  06 ** ** 22 12
     *
     * @param val
     * @return
     */
    public static String phoneEncrypt(String val, Integer digits) {
        if (digits == null) {
            digits = 0;
        }

        StringBuilder asterisks = new StringBuilder();
        for (int i = 1; i <= digits; i++) {
            asterisks.append("*");
        }

        return getStrAddSpace(stringTrim(val).replaceAll("(\\d{2})\\d{" + digits + "}(\\d{4})", "$1" + asterisks + "$2"), 2, 1);
    }

    /**
     * 将字符中间加空格 空格数
     *
     * @param val        目标字符串
     * @param intervalsN 字符串 按几位分割
     * @param spaceN     分割后 中间加几个空格
     * @return
     */
    public static String getStrAddSpace(String val, Integer intervalsN, Integer spaceN) {
        if (intervalsN == null) {
            intervalsN = 0;
        }
        if (spaceN == null) {
            spaceN = 0;
        }
        return val.replaceAll("(.{" + intervalsN + "})", "$" + spaceN + " ");
    }

    public static void main(String[] args) {
        String test = "0667322212";
        String encrypt = phoneEncrypt(test, 4);
        System.out.println(encrypt);

    }
}
