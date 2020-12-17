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

    /**
     * 根据model获取type
     * @param model
     * @return
     */
    public static int getScooterModelType(String model) {
        int scooterModel = 0;
        for (ScooterModelEnum m : ScooterModelEnum.values()) {
            if (m.getModel().equals(model)) {
                scooterModel = m.getType();
            }
        }
        return scooterModel;
    }

    /**
     * 根据type获取model
     * @param type
     * @return
     */
    public static String getScooterModelByType(Integer type) {
        String scooterModel = null;
        for (ScooterModelEnum m : ScooterModelEnum.values()) {
            if (m.getType().equals(type)) {
                scooterModel = m.getModel();
            }
        }
        return scooterModel;
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
