package com.redescooter.ses.api.common.vo.jiguang;


import com.redescooter.ses.api.common.enums.proxy.jiguang.PlatformTypeEnums;
import com.redescooter.ses.api.common.enums.proxy.jiguang.PushTypeEnums;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.*;

import java.util.Map;

/**
 * description: PushBeanEnter 极光推送
 * author: jerry.li
 * create: 2019-05-20 11:04
 */

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class PushBeanEnter extends GeneralEnter {

    // 必填, 通知内容, 内容可以为空字符串，则表示不展示到通知栏。
    private String alert;
    // 可选, 附加信息, 供业务使用。
    private Map<String, String> extras;
    //android专用// 可选, 通知标题	如果指定了，则通知里原来展示 App名称的地方，将展示成这个字段。
    private String title;
    //极光注册ID
    private String[] registids;
    //设备类型  当前支持 Android, iOS, Windows Phone 三个平台的推送.
    private String type = PlatformTypeEnums.ANDROID.getCode();
    //推送目标，默认根据设备唯一标识进行推送
    private String pushAimsType = PushTypeEnums.REGISTRATION_ID.getCode();

}
