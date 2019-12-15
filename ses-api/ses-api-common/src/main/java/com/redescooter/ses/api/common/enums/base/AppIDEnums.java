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

    DELIVERY_WEB("DELIVERY_WEB", SystemIDEnums.REDE_DELIVERY.getSystemId(), 1, "SaaS配送"),
    DELIVERY_APP("DELIVERY_APP", SystemIDEnums.REDE_DELIVERY.getSystemId(), 2, "SaaS移动"),
    REPAIR_WEB("REPAIR_WEB", SystemIDEnums.REDE_DELIVERY.getSystemId(), 3, "SaaS维修"),
    CRM_WEB("CRM_WEB", SystemIDEnums.REDE_SES.getSystemId(), 4, "内部管理"),
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
