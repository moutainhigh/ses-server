package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 22/11/2019 5:00 下午
 * @ClassName: AppIDEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
public enum AppIDEnums {

    SAAS_WEB("SAAS_WEB", SystemIDEnums.REDE_SAAS.getSystemId(), 1, "SaaS配送"),
    SAAS_APP("SAAS_APP", SystemIDEnums.REDE_SAAS.getSystemId(), 2, "SaaS移动"),
    SAAS_REPAIR_WEB("SAAS_REPAIR_WEB", SystemIDEnums.REDE_SAAS.getSystemId(), 3, "SaaS维修"),
    SES("SES", SystemIDEnums.REDE_SES.getSystemId(), 4, "RedE办公系统"),
    DEV("DEV", SystemIDEnums.REDE_DEV.getSystemId(), 5, "RedE开发系统"),
    ;

    //应用ID
    private String appId;
    //系统ID
    private String systemId;
    //应用值
    private Integer value;
    //编码备注说明
    private String remark;


}
