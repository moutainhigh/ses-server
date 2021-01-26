package com.redescooter.ses.api.common.enums.date;

/**
 * 月份码枚举类
 * @author assert
 * @date 2021/1/15 11:45
 */
public enum MonthCodeEnum {

    JANUARY(1, "1", "一月"),
    FEBRUARY(2, "2", "二月"),
    MARCH(3, "3", "三月"),
    APRIL(4, "4", "四月"),
    MAY(5, "5", "五月"),
    JUNE(6, "6", "六月"),
    JULY(7, "7", "七月"),
    AUGUST(8, "8", "八月"),
    SEPTEMBER(9, "9", "九月"),
    OCTOBER(10, "A", "十月"),
    NOVEMBER(11, "B", "十一月"),
    DECEMBER(12, "C", "十二月")
    ;

    private Integer month;
    private String code;
    private String desc;

    MonthCodeEnum(Integer month, String code, String desc) {
        this.month = month;
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据月份获取月份码
     * @param month
     * @return
     */
    public static String getMonthCodeByMonth(Integer month) {
        String monthCode = null;
        for (MonthCodeEnum m : MonthCodeEnum.values()) {
            if (m.getMonth().equals(month)) {
                monthCode = m.getCode();
            }
        }
        return monthCode;
    }


    public Integer getMonth() {
        return month;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
