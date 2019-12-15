package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mr.lijiating
 * @version V1.0
 * @Date: 22/11/2019 5:48 下午
 * @ClassName: AccountTypeEnums
 * @Function: TODO
 */
@Getter
@AllArgsConstructor
public enum AccountTypeEnums {

    WEB_REPAIR(SystemIDEnums.REDE_DELIVERY.getSystemId(), AppIDEnums.REPAIR_WEB.getAppId(), BusinessIDEnums.NORMAL.getCode(), 1),
    WEB_RESTAURANT(SystemIDEnums.REDE_DELIVERY.getSystemId(), AppIDEnums.DELIVERY_WEB.getAppId(), BusinessIDEnums.RESTAURANT.getCode(), 2),
    WEB_EXPRESS(SystemIDEnums.REDE_DELIVERY.getSystemId(), AppIDEnums.DELIVERY_WEB.getAppId(), BusinessIDEnums.EXPRESS.getCode(), 3),
    APP_RESTAURANT(SystemIDEnums.REDE_DELIVERY.getSystemId(), AppIDEnums.DELIVERY_APP.getAppId(), BusinessIDEnums.RESTAURANT.getCode(), 4),
    APP_EXPRESS(SystemIDEnums.REDE_DELIVERY.getSystemId(), AppIDEnums.DELIVERY_APP.getAppId(), BusinessIDEnums.EXPRESS.getCode(), 5),
    APP_PERSONAL(SystemIDEnums.REDE_DELIVERY.getSystemId(), AppIDEnums.DELIVERY_APP.getAppId(), BusinessIDEnums.PERSONAL.getCode(), 6),
    WEB_CRM(SystemIDEnums.REDE_SES.getSystemId(), AppIDEnums.CRM_WEB.getAppId(), BusinessIDEnums.NORMAL.getCode(), 7),
    ;

    //系统id
    private String systemId;
    //应用id
    private String appId;
    //业务范围id
    private String businessId;
    //账户类型
    private Integer accountType;

}
