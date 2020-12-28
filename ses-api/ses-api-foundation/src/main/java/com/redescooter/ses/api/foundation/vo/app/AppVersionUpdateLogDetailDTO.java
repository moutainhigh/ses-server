package com.redescooter.ses.api.foundation.vo.app;

import lombok.Data;

import java.util.Date;

/**
 * @author assert
 * @date 2020/12/27 17:12
 */
@Data
public class AppVersionUpdateLogDetailDTO {

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
     * 应用版本code
     */
    private String versionCode;

    /**
     * 更新包下载地址
     */
    private String updateLink;

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
