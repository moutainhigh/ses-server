package com.redescooter.ses.api.common.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @ClassName:ClientTypeEnums
 * @description: ClientTypeEnums
 * @author: Alex
 * @Version：1.3
 * @create: 2020/08/21 11:54
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ClientTypeEnums {
    
    APP_IOS("APP_IOS","苹果APP","IOS"),
    APP_ANDROID("APP_ANDROID","安卓APP","Android"),
    PC("PC","电脑端","PC"),
    IPAD("IPAD","移动电脑端","IPAD");
    
    private String code;
    
    private String message;
    
    private String value;
}
