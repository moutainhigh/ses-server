package com.redescooter.ses.starter.emqx.constants;

/**
 * EMQ X消息下发/订阅Topic主题常量类
 * @author assert
 * @date 2020/11/18 11:32
 */
public interface EmqXTopicConstant {

    // 消息下发主题Topic(后台往平板端发送消息通知)
    /**
     * 车辆开关锁操作
     */
    String SCOOTER_LOCK_TOPIC = "scooter-%s/device-lock";
    /**
     * 车辆开始/结束导航操作
     */
    String SCOOTER_NAVIGATION_TOPIC = "scooter-%s/device-navigation";
    /**
     * 车辆平板更新操作
     */
    String SCOOTER_TABLET_UPDATE_TOPIC = "scooter-%s/device-tablet-update";
    /**
     * 设置车辆型号
     */
    String SET_SCOOTER_MODEL_TOPIC = "scooter-%s/set-model";

    /**
     * 同步订单数量
     */
    String SYNC_ORDER_QUANTITY_TOPIC = "scooter-%s/syncOrderQuantity";

    // 消息订阅主题Topic(车辆平板端往后台上报车辆数据)
    /**
     * 车辆-ECU(仪表)数据上报
     */
    String SCOOTER_ECU_REPORTED_TOPIC = "scooter/device-ecu";
    /**
     * 车辆-BBI(电池管理系统)数据上报
     */
    String SCOOTER_BBI_REPORTED_TOPIC = "scooter/device-bbi";
    /**
     * 车辆-BMS(电池)数据上报
     */
    String SCOOTER_BMS_REPORTED_TOPIC = "scooter/device-bms";
    /**
     * 车辆-MCU(控制器)数据上报
     */
    String SCOOTER_MCU_REPORTED_TOPIC = "scooter/device-mcu";
    /**
     * 车辆-整车信息上报
     */
    String SCOOTER_ALL_REPORTED_TOPIC = "scooter/device-all";
    /**
     * 车辆-锁开关状态上报
     */
    String SCOOTER_LOCK_REPORTED_TOPIC = "scooter/device-lock";

}
