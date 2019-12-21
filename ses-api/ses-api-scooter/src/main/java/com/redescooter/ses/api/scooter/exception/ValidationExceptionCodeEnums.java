package com.redescooter.ses.api.scooter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ValidationExceptionCodeEnums
 * @description: ValidationExceptionCodeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2019/10/31 17:06
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ValidationExceptionCodeEnums {

    SCOOTERID_IS_EMPTY(1001, "scooterId 为空"),

    EVENT_IS_EMPTY(1002, "事件 为空"),

    LONGITUDE_IS_EMPTY(1003, "经度为空"),

    LATITUDE_IS_EMPTY(1004, "维度为空"),

    ACTION_RESULT_IS_EMPTY(1005, "Action 结果为空"),

    GEOHASH_IS_EMPTY(1006, "geohash为空"),

    BATTERY_IS_EMPTY(1007, "电量为空"),

    BEGINTIME_IS_EMPTY(1008, "开始时间为空"),

    ENDTIME_IS_EMPTY(1009, "结束时间为空"),

    DESTINATION_IS_NOT_EMPTY(1010, "目的地为空"),

    DESTINATION_LATITUDE_IS_NOT_EMPTY(1011, "目的地经度为空"),

    DESTINATION_LONGITUDE_IS_NOT_EMPTY(1012, "目的地 维度为空"),

    SCOOTER_STATUS_IS_EMPTY(1013, "scooter 状态为空"),

    REPAIR_TYPE_IS_EMPTY(1014, "维修类型为空"),

    BOOKTIME_IS_EMPTY(1015, "预约时间为空"),

    REPAIT_SCOOTER_STATUS_IS_EMPTY(1016, "维修sooter的状态为空"),

    SCOOTER_LICENSE_IS_EMPTY(1017, "车牌号为空"),

    SCOOTER_LICENSE_PICTURE_IS_EMPTY(1018, "车牌号图为空"),

    SCOOTER_LICENSE_TIME_IS_EMPTY(1019, "上牌时间为空")
    ;
    private int code;

    private String message;


    public static ValidationExceptionCodeEnums getErrorCodeByCode(int code) {
        for (ValidationExceptionCodeEnums item : ValidationExceptionCodeEnums.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return null;
    }
}
