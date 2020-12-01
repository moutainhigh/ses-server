package com.redescooter.ses.api.common.enums.scooter;

/**
 * 车辆操作类型枚举类
 * @author Alex
 * @date 2019/12/27 14:39
 */
public enum ScooterActionTypeEnums {

    /**
     * 车辆操作类型
     */
    UNLOCK("UNLOCK", "解锁", "1"),
    LOCK("LOCK","上锁","2"),
    START_NAVIGATION("START_NAVIGATION","开启导航","3"),
    END_NAVIGATION("END_NAVIGATION","结束导航","4"),
    TABLET_UPDATE("TABLET_UPDATE", "车辆平板升级", "5")
    ;


    private String code;
    private String message;
    private String value;

    ScooterActionTypeEnums(String code, String message, String value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getValue() {
        return value;
    }

}
