package com.redescooter.ses.api.foundation.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.redescooter.ses.api.common.vo.base.GeneralResult;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * description: JpushUserResult
 * author: jerry.li
 * create: 2019-05-22 11:30
 */

@Data
@Builder
public class JpushUserResult extends GeneralResult {

    private Long id;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 设备唯一标识
     */
    private String registrationId;

    /**
     * 标签
     */
    private String tag;

    /**
     * 别名
     */
    private String alias;

    /**
     * 推送平台:支持 Android, iOS, Windows Phone 三个平台的推送。其关键字分别为："android", "ios", "winphone"。
     */
    private String platformType;

    /**
     * 推送目标:别名、标签、注册 ID、分群、广播
     */
    private String audienceType;

    /**
     * 推送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date pushTime;

}
