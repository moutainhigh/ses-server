package com.redescooter.ses.tool.utils.chart;

import com.redescooter.ses.tool.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 18/2/2020 11:37 上午
 * @ClassName: OrderChartUtils
 * @Function: TODO
 */
public class OrderChartUtils {

    /**
     * 根据天数，补全时间，供单据图表使用
     *
     * @param heavens
     * @param date
     * @return
     */
    public static List<String> getDateList(int heavens, Date date) {
        ArrayList<String> list = new ArrayList<>();
        switch (heavens) {
            case 1:
                list = DateUtil.get24HourList(DateUtil.getDateTimeStamp(date));
                break;
            case 7:
                list = DateUtil.getDayList(date, 7, null);
                break;
            case 30:
                list = DateUtil.getDayList(date, 30, null);
                break;
            case 365:
                list = DateUtil.getDayList(date, 365, DateUtil.DEFAULT_YYMM_FORMAT);
                break;
        }

        return checkDayResultSingle(list);
    }

    //去除重复的时间，只供柱状图使用。
    private static ArrayList<String> checkDayResultSingle(ArrayList<String> dayList) {
        ArrayList<String> temp = new ArrayList<String>();
        Iterator<String> iterator = dayList.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (!temp.contains(str)) {
                temp.add(str);
            }
        }
        return temp;
    }
}
