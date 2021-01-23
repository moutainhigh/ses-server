package com.redescooter.ses.web.website.demo;

import com.redescooter.ses.api.common.constant.DateConstant;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author jerry
 * @Date 2021/1/23 2:06 上午
 * @Description 测试demo2
 **/
public class DemoTest2 {

    @Test
    public void test1() {
//        BigDecimal bigDecimal = new BigDecimal("180.12").multiply(new BigDecimal("120"));
//
//        System.out.printf(bigDecimal.toString());
//
//        System.out.printf("-----");
        BigDecimal total = new BigDecimal("0");
        for (int i = 0; i < 100; i++) {
            System.out.println(new BigDecimal("2").multiply(new BigDecimal("2")));
            total = total.add(new BigDecimal("2").multiply(new BigDecimal("2")));
            System.out.println("i ===========" + i);
            System.out.println(total);
        }

        System.out.printf("--------------");
        System.out.println(total.toPlainString());

    }

    @Test
    public void test2() {
        SimpleDateFormat s = new SimpleDateFormat(DateConstant.DEFAULT_DATETIME_FORMAT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7);//计算45天后的时间
        String str2 = s.format(c.getTime());
        System.out.println("7天后的时间是：" + str2);
    }

    @Test
    public void test3() {

        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int mi = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);
        System.out.println("现在时刻是" + y + "年" + m + "月" + d + "日" + h + "时" + mi + "分" + s + "秒");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddss");
        System.out.println(dateFormat.format(calendar.getTime()));

        String id = "13131313133l";

        String substring = id.substring(6);
        System.out.println(substring);
    }
}
