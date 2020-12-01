package com.redescooter.ses.api.scooter.vo.emqx;

import lombok.Data;

/**
 * EMQ X向车辆平板端下发更新操作消息 DTO
 * @author assert
 * @date 2020/11/25 15:54
 */
@Data
public class ScooterTabletUpdatePublishDTO {

    /**
     * 车辆平板序列号
     */
    private String tabletSn;

    /**
     * 更新包下载地址
     */
    private String downloadLink;

    /**
     * 版本应用编码
     */
    private String versionCode;

    /**
     * true升级 false回滚,布尔值禁止使用is开头
     */
    private boolean updateOrRollBack;

}
