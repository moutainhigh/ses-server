package com.redescooter.ses.api.common.enums.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description: ScooterStatus
 * @desc
 * @author: Alex
 * @create: 2019/01/22 09:52
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ScooterStatusEnums {
    NOTEXIST("NOTEXIST", "Scooter is not exist", "车辆不存在", "1"),
    ALLOCATION("ALLOCATION", "Allocation to scooter", "已分配", "2"),
    USED("USED", "Use to scooter", "使用中", "3"),
    FINSH("FINSH", "FINSH scooter", "归还车辆", "4"),
    REPAIRING("REPAIRING", "repair scooter", "车辆维修中", "5");

    private String code;

    private String message;

    private String name;

    private String value;

    public static ScooterStatusEnums getScooterStatus(String code) {
        for (ScooterStatusEnums item : ScooterStatusEnums.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
