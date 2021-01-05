package com.redescooter.ses.service.foundation.dm.base;

import java.util.Date;
import lombok.Data;

/**
 * @author assert
 * @date 2020/12/27 18:15
 */
@Data
public class PlaAppVersionUpdateLog {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 逻辑删除标识 0正常 1删除
     */
    private Integer dr;

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