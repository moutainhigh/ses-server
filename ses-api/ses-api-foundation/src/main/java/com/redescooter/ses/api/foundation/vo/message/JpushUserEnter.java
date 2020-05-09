package com.redescooter.ses.api.foundation.vo.message;

import com.redescooter.ses.api.common.vo.base.GeneralEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * description: JpushUserEnter
 * author: jerry.li
 * create: 2019-05-22 11:34
 */

@Data //生成getter,setter等函数
@AllArgsConstructor //生成全参数构造函数
@NoArgsConstructor//生成无参构造函数
@Builder
@EqualsAndHashCode(callSuper = false)
public class JpushUserEnter extends GeneralEnter {


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
     * 登录绑定：0，注销解绑：1
     */
    private Integer status;

}
