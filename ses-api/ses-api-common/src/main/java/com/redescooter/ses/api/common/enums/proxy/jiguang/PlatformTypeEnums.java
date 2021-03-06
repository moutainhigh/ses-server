package com.redescooter.ses.api.common.enums.proxy.jiguang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * description: PlatformTypeEnums
 * author: jerry.li
 * create: 2019-05-20 18:36
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PlatformTypeEnums {

    ALL("All", "all", "全部设备"),
    ANDROID("Android", "android", "安卓"),
    IOS("IOS", "ios", "苹果"),
    PC("PC", "pc", "电脑桌面"),
    NONE("NONE","none","暂无平台"),
    ;
    //系统及移动端使用
    private String code;
    //记录极光用户关系使用
    private String value;

    private String name;
}
