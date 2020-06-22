package com.redescooter.ses.api.common.enums.base;

/**
 * @Author Aleks
 * @Description
 * @Date  2020/6/17 19:25
 * @Param
 * @return
 **/
public enum AppSysTypeEnum {

    APP_IOS(1,"IOS"),

    APP_ANDROID(2,"ANDROID");


    private final int value;
    private final String name;

    AppSysTypeEnum(Integer value, String name) {
        this.value = value;

        this.name = name;
    }


    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static AppSysTypeEnum get(int type){
        for (AppSysTypeEnum value : values()) {
            if (value.getValue() == type) {
                return value;
            }
        }
        return null;
    }








}
