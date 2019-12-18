package com.redescooter.ses.tool.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * @description: OSSClientUtil
 * @author: Alex
 * @create: 2019/03/18 15:33
 */
@Slf4j
public class YearCompleteUtil {

    public static Integer yearComplete(Date birthday) {
        Calendar cal = Calendar.getInstance();
        //使用默认时区和语言环境获得一个日历。
        int year = cal.get(Calendar.YEAR);
        return (year - birthday.getYear());
    }
    public static void main(String[] args){
      Integer test=YearCompleteUtil.yearComplete(new Date(1970,2,15));
        log.info(test.toString());
    }
}