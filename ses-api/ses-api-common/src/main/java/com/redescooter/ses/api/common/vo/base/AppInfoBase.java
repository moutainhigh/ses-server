package com.redescooter.ses.api.common.vo.base;

import lombok.Data;
import lombok.ToString;

/**
 * APP应用信息存储
 */
@Data
@ToString
public class AppInfoBase {

    private String name;
    private String appkey;
    private String secret;
    private Boolean apns;

}
