package com.redescooter.ses.api.foundation.vo.app;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用版本升级记录 DTO类
 * @author assert
 * @date 2020/12/27 15:51
 */
@Data
public class AppVersionUpdateLogDTO implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 车辆平板序列号
     */
    private String tabletSn;

    /**
     * 应用版本id
     */
    private Long appVersionId;

    /**
     * 应用类型 1-IOS 2-ANDROID 3-SCS(车载平板) 4-SaaS 5-Server(后台服务) 6-ROS
     */
    private Integer appVersionType;

    /**
     * 是否升级成功 false否 true是(数据库中0表示false，1表示true)
     */
    private Boolean isUpdateSuccess;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

}
