package com.redescooter.ses.web.website.demo;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.redescooter.ses.api.common.constant.DateConstant;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

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
        GeneralEnter enter = null;
        if (StringUtils.isEmpty(enter)) {
            System.out.println("11111");
        }


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
