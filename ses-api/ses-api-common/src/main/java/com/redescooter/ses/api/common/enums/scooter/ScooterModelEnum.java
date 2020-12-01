package com.redescooter.ses.api.common.enums.scooter;

/**
 * 车辆类型枚举类
 * @author assert
 * @date 2020/12/1 9:58
 */
public enum ScooterModelEnum {

    /**
     * 车辆类型
     */
    SCOOTER_E25(1, "25km/h", "E25"),
    SCOOTER_E50(2, "45km/h", "E50"),
    SCOOTER_E100(3, "80km/h", "E100"),
    SCOOTER_E125(4, "120km/h", "E125")
    ;

    private Integer type;
    private String speed;
    private String model;

    ScooterModelEnum(Integer type, String speed, String model) {
        this.type = type;
        this.speed = speed;
        this.model = model;
    }

    public Integer getType() {
        return type;
    }

    public String getSpeed() {
        return speed;
    }

    public String getModel() {
        return model;
    }

}
