package com.redescooter.ses.starter.redis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@EqualsAndHashCode(callSuper = false)
public class MessageGeneralEnter implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 系统id
     */
    private String systemId;

    /**
     * APPid
     */
    private String appId;

    /**
     * token值
     */
    private String token;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 版本
     */
    private String version;

    /**
     * 时间
     */
    private Long timestamp;

    /**
     * 客户端类型
     */
    private String clientType;

    /**
     * 客户端id
     */
    private String clientIp;

    /**
     * 时区
     */
    private String timeZone;

    /**
     * 语言
     */
    private String language;

    /**
     * 城市
     */
    private String country;
}
