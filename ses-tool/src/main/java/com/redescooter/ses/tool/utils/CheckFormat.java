package com.redescooter.ses.tool.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * description: CheckPhone 目前暂支持中国手机验证，带其他国家确定后进行支持
 * author: jerry.li
 * <p>
 * 国家/地区 语言代码 国家/地区 语言代码
 * <p>
 * 简体中文(中国)	zh-cn	繁体中文(台湾地区)	zh-tw
 * 法语(瑞士)	fr-ch	法语(比利时)	fr-be
 * create: 2019-05-31 16:56
 */

public class CheckFormat {

    public final static Map map = new HashMap();

    static {
        //法国
        map.put("fr-FR", "/^(\\+?33|0)[67]\\d{8}$/");
        //中国
        map.put("zh-CN", "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$");

    }

    //验证手机号
    public static boolean isPhone(String countrycode, String phone) {

        String regex = null;
        if (isNullOrEmpty(countrycode)) {
            countrycode = "+86";
        }

        if ("+86".equals(countrycode)) {
            regex = map.get("zh-CN").toString();
        } else {
            regex = map.get("fr-FR").toString();
        }
        if (isNullOrEmpty(phone)) {
            return false;
        }

        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    //邮箱
    public static boolean isEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

}
