package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

/**
 * EMQ X向车辆平板端下发设置车型消息 DTO
 * @author assert
 * @date 2020/12/1 10:59
 */
@Data
public class SetScooterModelPublishDTO {

    /**
     * 平板序列号
     */
    private String tabletSn;

    /**
     * 车辆类型 1(25km/h),2(45km/h),3(80km/h),4(120km/h)
     */
    private Integer type;

}
