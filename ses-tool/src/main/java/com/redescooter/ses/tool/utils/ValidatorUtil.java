package com.redescooter.ses.tool.utils;

import java.util.regex.Pattern;

/**
 * @Description 正则表达式校验器
 * @Author Chris
 * @Date 2021/2/23 17:00
 */
public class ValidatorUtil {

    /**
     * 正则表达式:只能输入数字
     */
    public static final String REGEX_NUMBER = "^[0-9]*$";

    public static boolean isNumber(String code) {
        return Pattern.matches(REGEX_NUMBER, code);
    }

}
