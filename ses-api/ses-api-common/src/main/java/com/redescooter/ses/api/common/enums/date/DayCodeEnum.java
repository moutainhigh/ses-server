package com.redescooter.ses.api.common.enums.date;

/**
 * 天数码枚举类
 * @author assert
 * @date 2021/1/15 11:45
 */
public enum DayCodeEnum {

    ONE(1, "1", "一号"),
    TWO(2, "2", "二号"),
    THREE(3, "3", "三号"),
    FOUR(4, "4", "四号"),
    FIVE(5, "5", "五号"),
    SIX(6, "6", "六号"),
    SEVEN(7, "7", "七号"),
    EIGHTH(8, "8", "八号"),
    NINE(9, "9", "九号"),
    TEN(10, "A", "十号"),
    ELEVEN(11, "B", "十一号"),
    TWELVE(12, "C", "十二号"),
    THIRTEEN(13, "D", "十三号"),
    FOURTEEN(14, "E", "十四号"),
    FIFTEEN(15, "F", "十五号"),
    SIXTEEN(16, "G", "十六号"),
    SEVENTEEN(17, "H", "十七号"),
    EIGHTEEN(18, "J", "十八号"),
    NINETEEN(19, "K", "十九号"),
    TWENTY(20, "L", "二十号"),
    TWENTY_ONE(21, "M", "二十一号"),
    TWENTY_TWO(22, "N", "二十二号"),
    TWENTY_THREE(23, "P", "二十三号"),
    TWENTY_FOUR(24, "Q", "二十四号"),
    TWENTY_FIVE(25, "R", "二十五号"),
    TWENTY_SIX(26, "S", "二十六号"),
    TWENTY_SEVEN(27, "T", "二十七号"),
    TWENTY_EIGHTH(28, "U", "二十八号"),
    TWENTY_NINE(29, "V", "二十九号"),
    THIRTY(30, "W", "三十号"),
    THIRTY_ONE(31, "X", "三十一号");

    private Integer day;
    private String code;
    private String desc;

    DayCodeEnum(Integer day, String code, String desc) {
        this.day = day;
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据天数获取天数码
     * @param day
     * @return
     */
    public static String getDayCodeByDay(Integer day) {
        String dayCode = null;
        for (DayCodeEnum d : DayCodeEnum.values()) {
            if (d.getDay().equals(day)) {
                dayCode = d.getCode();
            }
        }
        return dayCode;
    }

    public Integer getDay() {
        return day;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
