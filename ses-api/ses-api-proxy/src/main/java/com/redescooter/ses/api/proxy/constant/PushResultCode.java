package com.redescooter.ses.api.proxy.constant;

/**
 * @ClassName:PushResultCode
 * @description: PushResultCode
 * @author: Alex
 * @Version：1.3
 * @create: 2019/10/31 13:59
 */
public interface PushResultCode {

    int SUCCESS_CODE = 1;

    int FAILURE_CODE = 0;

    String SUCCESS_MESSAGE="";

    int DEFAULT_CODE=3;

    int DEFAULT_MSGID=0;

    int DEFAULT_SEND_NO=0;
    // 区分成功状态的code  默认值为 5
    int DEFAULT_STATUS_CODE=5;

    String DEFAULT_MESSAGE="";
}
