package com.redescooter.ses.api.scooter.exception;

/**
 * @description: ValidationExceptionCode
 * @author: Darren
 * @create: 2019/01/17 10:26
 */
public interface ValidationExceptionCode {
    //scooterID 爲空
    int SCOOTERID_IS_EMPTY = 1001;
    //scooter 事件 为空
    int EVENT_IS_EMPTY = 1002;
    // 用户经度为空
    int LONGITUDE_IS_EMPTY = 1003;
    //用户维度为空
    int LATITUDE_IS_EMPTY =1004;
    // action 结果为空
    int ACTION_RESULT_IS_EMPTY =1005;
    //geohash为空
    int GEOHASH_IS_EMPTY=1006;
    //电量为空
    int BATTERY_IS_EMPTY=1007;
    //开始时间为空
    int BEGINTIME_IS_EMPTY =1008;
    //结束时间为空
    int ENDTIME_IS_EMPTY =1009;
    //目的地为空
    int DESTINATION_IS_NOT_EMPTY = 1010;
    //目的地维度为空
    int DESTINATION_LATITUDE_IS_NOT_EMPTY = 1011;
    //目的地经度为空
    int DESTINATION_LONGITUDE_IS_NOT_EMPTY =1012;
    //scooter 状态为空
    int SCOOTER_STATUS_IS_EMPTY =1013;
    //维修类型为空
    int REPAIR_TYPE_IS_EMPTY =1014;
    //预约时间为空
    int BOOKTIME_IS_EMPTY =1015;
    //维修sooter的状态为空
    int REPAIT_SCOOTERSTATUS_IS_EMPTY=1016;
    //车牌号为空
    int SCOOTER_LICENSE_IS_EMPTY=1017;
    // 车牌号图片不能空
    int SCOOTER_LICENSE_PICTURE_IS_EMPTY=1018;
    // 上牌时间为空
    int SCOOTER_LICENSE_TIME_IS_EMPTY= 1019;
    //  保险为空
    int SCOOTER_INSURANCE_IS_EMPTY=1020;
    // 保险时间为空
    int SCOOTER_INSURANCE_TIME_IS_EMPTY=1021;
    // 车辆编号为空
    int SCOOTER_NO_IS_EMPTY=1022;
    // 车辆 型号为空
    int SCOOTER_IS_MODEL_EMPTY=1023;
}
