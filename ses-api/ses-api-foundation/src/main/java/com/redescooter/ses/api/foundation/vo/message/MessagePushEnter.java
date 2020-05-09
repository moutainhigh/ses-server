package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.enums.jiguang.PlatformTypeEnum;
import com.redescooter.ses.api.common.enums.jiguang.PushTypeEnum;
import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * description: MessagePushEnter 极光推送
 * author: jerry.li
 * create: 2019-05-20 11:04
 */

@Data
@Builder
public class MessagePushEnter extends GeneralEnter {

    // 必填, 通知内容, 内容可以为空字符串，则表示不展示到通知栏。
    private String alert;

    // 可选, 附加信息, 供业务使用。
    private Map<String, String> extras;

    //android专用// 可选, 通知标题	如果指定了，则通知里原来展示 App名称的地方，将展示成这个字段。
    private String title;

    //用户ID
    private String[] userIds;

    //设备类型  当前支持 Android, iOS, Windows Phone 三个平台的推送.
    private String type = PlatformTypeEnum.ANDROID.getCode();

    //推送目标，默认根据设备唯一标识进行推送
    private String pushAimsType = PushTypeEnum.REGISTRATION_ID.getCode();

    //订单创建人，用户PC端推送时获取推送人相关信息
    private Long createUser;

}
