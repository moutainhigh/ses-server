package com.redescooter.ses.service.foundation.dm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JpushUserData implements Serializable {

    private Long id;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 设备唯一标识
     */
    private String registrationId;

    /**
     * 标签
     */
    private String tag;

    /**
     * 别名
     */
    private String alias;

    /**
     * 推送平台:支持 Android, iOS, Windows Phone 三个平台的推送。其关键字分别为："android", "ios", "winphone"。
     */
    private String platformType;

    /**
     * 推送目标:别名、标签、注册 ID、分群、广播
     */
    private String audienceType;

    /**
     * 推送时间
     */
    private Date pushTime;

    /**
     * 登录绑定：0，注销解绑：1
     */
    private Integer status;

    /**
     * 状态码：登录绑定LOGIN，注销解绑LOGOUT
     */
    private String statusCode;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createBy;

    private static final long serialVersionUID = 1L;
}